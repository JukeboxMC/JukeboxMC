package org.jukeboxmc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.console.TerminalConsole;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerListPacket;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.Listener;
import org.jukeboxmc.network.raknet.event.intern.PlayerCloseConnectionEvent;
import org.jukeboxmc.network.raknet.event.intern.PlayerConnectionSuccessEvent;
import org.jukeboxmc.network.raknet.event.intern.ReciveMinecraftPacketEvent;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.plugin.PluginManager;
import org.jukeboxmc.scheduler.Scheduler;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.EmptyGenerator;
import org.jukeboxmc.world.generator.FlatGenerator;
import org.jukeboxmc.world.generator.WorldGenerator;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Server {

    @Getter
    @Setter
    private static Server instance;

    private InetSocketAddress address;
    private Listener listener;

    private File pluginFolder;

    private Logger logger;
    private Config serverConfig;
    private PluginManager pluginManager;
    private TerminalConsole console;

    private Scheduler scheduler;
    private ScheduledFuture<?> tickFuture;
    private ScheduledExecutorService tickExecutor;

    private World defaultWorld;
    private WorldGenerator overWorldGenerator;

    private int viewDistance = 32;
    private int currentTick = 0;

    private boolean isShutdown = false;

    private Map<InetSocketAddress, Player> players = new HashMap<>();
    private Map<UUID, PlayerListPacket.Entry> playerListEntry = new HashMap<>();
    private Map<String, World> worlds = new HashMap<>();
    private Map<String, WorldGenerator> worldGenerator = new HashMap<>();

    public Server( Logger logger ) {
        Server.setInstance( this );
        this.logger = logger;

        this.pluginFolder = new File( "./plugins" );
        if ( !this.pluginFolder.exists() ) {
            this.pluginFolder.mkdirs();
        }

        this.initServerConfig();

        this.console = new TerminalConsole( this );
        this.console.getConsoleThread().start();

        this.scheduler = new Scheduler( this );

        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        builder.setNameFormat( "JukeboxMC Tick Executor" );
        this.tickExecutor = Executors.newScheduledThreadPool( 1, builder.build() );
        this.tickFuture = this.tickExecutor.scheduleAtFixedRate( this::tickProcess, 50, 50, TimeUnit.MILLISECONDS );

        BlockType.init();
        ItemType.init();

        this.address = new InetSocketAddress( this.serverConfig.getString( "address" ), this.serverConfig.getInt( "port" ) );

        this.registerGenerator( "Flat", FlatGenerator.class );
        this.registerGenerator( "Empty", EmptyGenerator.class );
        this.overWorldGenerator = this.worldGenerator.get( this.serverConfig.getString( "generator" ) );

        String defaultWorldName = this.serverConfig.getString( "defaultworld" );
        if ( this.loadOrCreateWorld( defaultWorldName ) ) {
            this.defaultWorld = this.getWorld( defaultWorldName );
        }

        this.pluginManager = new PluginManager( this );
        this.pluginManager.enableAllPlugins();

        Runtime.getRuntime().addShutdownHook( new Thread( this::shutdown ) );
    }

    public void startServer() {
        this.listener = new Listener( this );
        if ( !this.listener.listen( this.address ) ) {
            this.logger.error( "The server could not start, is it already running on the same port?" );
            return;
        }

        this.listener.getRakNetEventManager().onEvent( ReciveMinecraftPacketEvent.class, (Consumer<ReciveMinecraftPacketEvent>) event -> {
            Connection connection = event.getConnection();
            Packet packet = event.getPacket();
            Player player = this.players.get( connection.getSender() );
            player.getPlayerConnection().handlePacketSync( packet );
        } );

        this.listener.getRakNetEventManager().onEvent( PlayerConnectionSuccessEvent.class, (Consumer<PlayerConnectionSuccessEvent>) event -> {
            Connection connection = event.getConnection();

            Player player = new Player( this, connection );
            this.players.put( player.getAddress(), player );
            this.setOnlinePlayers( this.players.size() );
        } );

        this.listener.getRakNetEventManager().onEvent( PlayerCloseConnectionEvent.class, (Consumer<PlayerCloseConnectionEvent>) event -> {
            Connection connection = event.getConnection();
            String reason = event.getReason();
            Player player = this.players.get( connection.getSender() );
            if ( player != null && player.isSpawned() ) {
                player.getPlayerConnection().leaveGame( reason );
            }
        } );
    }

    public void shutdown() {
        if ( this.isShutdown ) {
            return;
        }

        this.isShutdown = true;

        for ( Player onlinePlayer : this.getOnlinePlayers() ) {
            onlinePlayer.disconnect( "Server shutdown" );
        }

        this.pluginManager.disableAllPlugins();
        this.console.getConsoleThread().interrupt();
        this.tickExecutor.shutdown();
        this.scheduler.shutdown();
        this.listener.shutdown();
        for ( World world : this.getWorlds() ) {
            world.save();
        }

        this.logger.info( "Shutdown successfully!" );
    }

    private void tickProcess() {
        if ( this.isShutdown && !this.tickFuture.isCancelled() ) {
            this.tickFuture.cancel( false );
            this.listener.shutdown();
        }

        this.scheduler.onTick( ++this.currentTick );

        for ( Player player : this.players.values() ) {
            if ( player.isSpawned() ) {
                player.getPlayerConnection().update( this.currentTick );

            }
        }
        for ( World world : this.getWorlds() ) {
            if ( world != null ) {
                world.update( this.currentTick );
            }
        }
        for ( Player player : this.players.values() ) {
            player.getPlayerConnection().updateNetwork( this.currentTick );
        }
    }

    private void initServerConfig() {
        this.serverConfig = new Config( new File( System.getProperty( "user.dir" ) ), "properties.json" );
        this.serverConfig.addDefault( "address", "127.0.0.1" );
        this.serverConfig.addDefault( "port", 19132 );
        this.serverConfig.addDefault( "maxplayers", 20 );
        this.serverConfig.addDefault( "motd", "§bJukeboxMC" );
        this.serverConfig.addDefault( "gamemode", GameMode.CREATIVE.name() );
        this.serverConfig.addDefault( "defaultworld", "world" );
        this.serverConfig.addDefault( "generator", "flat" );
        this.serverConfig.save();
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Config getServerConfig() {
        return this.serverConfig;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public int getCurrentTick() {
        return this.currentTick;
    }

    public void setOnlinePlayers( int onlinePlayers ) {
        this.listener.getServerInfo().setOnlinePlayers( onlinePlayers );
    }

    public void setMaxPlayers( int maxPlayers ) {
        this.listener.getServerInfo().setMaxPlayers( maxPlayers );
    }

    public void setMotd( String motd ) {
        this.listener.getServerInfo().setMotd( motd );
    }

    public void setDefaultGamemode( GameMode defaultGamemode ) {
        this.listener.getServerInfo().setGameMode( defaultGamemode );
    }

    public void setDefaultWorld( World defaultWorld ) {
        this.defaultWorld = defaultWorld;
    }

    public GameMode getDefaultGamemode() {
        return this.listener.getServerInfo().getGameMode();
    }

    public String getMotd() {
        return this.listener.getServerInfo().getMotd();
    }

    public int getPort() {
        return this.address.getPort();
    }

    public String getHostname() {
        return this.address.getHostName();
    }

    public World getWorld( String name ) {
        for ( World world : this.worlds.values() ) {
            if ( world.getName().equalsIgnoreCase( name ) ) {
                return world;
            }
        }
        return null;
    }

    public World getDefaultWorld() {
        return this.defaultWorld;
    }

    public Collection<World> getWorlds() {
        return this.worlds.values();
    }

    public WorldGenerator getOverworldGenerator() {
        return this.overWorldGenerator;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public boolean isShutdown() {
        return this.isShutdown;
    }

    public Collection<Player> getOnlinePlayers() {
        return this.players.values();
    }

    public void removePlayer( InetSocketAddress address ) {
        this.players.remove( address );
    }

    public Map<UUID, PlayerListPacket.Entry> getPlayerListEntry() {
        return this.playerListEntry;
    }

    public boolean loadOrCreateWorld( String worldName ) {
        return this.loadOrCreateWorld( worldName, this.overWorldGenerator );
    }

    public boolean loadOrCreateWorld( String worldName, WorldGenerator worldGenerator ) {
        if ( !this.worlds.containsKey( worldName.toLowerCase() ) ) {
            World world = new World( worldName, worldGenerator );
            if ( world.loadLevelFile() && world.open() ) {
                this.worlds.put( worldName.toLowerCase(), world );
                this.logger.info( "Loading the world \"" + worldName + "\" was successful" );
                return true;
            } else {
                this.logger.error( "Failed to load world: " + worldName );
            }
        } else {
            this.logger.warn( "The world \"" + worldName + "\" was already loaded" );
        }
        return false;
    }

    public void unloadWorld( String worldName ) {
        this.unloadWorld( worldName, player -> {
            World world = this.getWorld( worldName );
            if ( world != null ) {
                if ( world == this.defaultWorld || this.defaultWorld == null ) {
                    player.disconnect( "World was unloaded" );
                } else {
                    player.teleport( this.defaultWorld.getSpawnLocation() );
                }
            }
        } );
    }

    public void unloadWorld( String worldName, Consumer<Player> consumer ) {
        World world = this.getWorld( worldName );
        if ( world != null ) {
            for ( Player player : world.getPlayers() ) {
                consumer.accept( player );
            }
            world.close();
        } else {
            this.logger.warn( "The world \"" + worldName + "\" was not found" );
        }
    }

    @SneakyThrows
    public void registerGenerator( String name, Class<? extends WorldGenerator> clazz ) {
        if ( !this.worldGenerator.containsKey( name ) ) {
            this.worldGenerator.put( name.toLowerCase(), clazz.newInstance() );
        }
    }

    public void broadcastMessage( String message ) {
        for ( Player onlinePlayers : this.players.values() ) {
            onlinePlayers.sendMessage( message );
        }
    }

    public void broadcastPacket( Packet packet ) {
        for ( Player onlinePlayers : this.players.values() ) {
            onlinePlayers.getPlayerConnection().sendPacket( packet );
        }
    }

    public File getPluginFolder() {
        return this.pluginFolder;
    }
}
