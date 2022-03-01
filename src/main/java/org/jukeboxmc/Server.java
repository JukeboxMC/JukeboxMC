package org.jukeboxmc;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.SneakyThrows;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.console.ConsoleSender;
import org.jukeboxmc.console.TerminalConsole;
import org.jukeboxmc.event.world.WorldLoadEvent;
import org.jukeboxmc.event.world.WorldUnloadEvent;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PacketRegistry;
import org.jukeboxmc.network.packet.PlayerListPacket;
import org.jukeboxmc.network.packet.type.TablistEntry;
import org.jukeboxmc.network.packet.type.TablistType;
import org.jukeboxmc.network.raknet.RakNetListener;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.plugin.PluginLoadOrder;
import org.jukeboxmc.plugin.PluginManager;
import org.jukeboxmc.resourcepack.ResourcePackManager;
import org.jukeboxmc.scheduler.Scheduler;
import org.jukeboxmc.utils.BedrockResourceLoader;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.EmptyGenerator;
import org.jukeboxmc.world.generator.FlatGenerator;
import org.jukeboxmc.world.generator.WorldGenerator;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Server {

    private static Server instance;
    private final Logger logger;
    private Config serverConfig;
    private Config operatorConfig;
    private GameMode defaultGameMode;
    private final PacketRegistry packetRegistry;
    private final RakNetListener rakNetListener;
    private InetSocketAddress address;
    private final ResourcePackManager resourcePackManager;
    private final PluginManager pluginManager;
    private final ConsoleSender consoleSender;
    private final TerminalConsole terminalConsole;
    private final Scheduler scheduler;

    private World defaultWorld;
    private final WorldGenerator overWorldGenerator;

    private final File pluginFolder;

    private String motd;
    private String subMotd;

    private final Thread mainThread;
    private final long serverId;
    private long currentTick = 0;

    private double currentTps = 20.0;

    private boolean running = true;

    private static final AtomicBoolean INITIATING = new AtomicBoolean( true );

    private final Object2ObjectMap<InetSocketAddress, Player> players = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<String, World> worlds = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<String, WorldGenerator> worldGenerator = new Object2ObjectOpenHashMap<>();
    private final Object2ObjectMap<UUID, TablistEntry> playerListEntry = new Object2ObjectOpenHashMap<>();
    private final BlockingQueue<Runnable> mainThreadWork = new LinkedBlockingQueue<>();

    @SneakyThrows
    public Server( Logger logger ) {
        Server.setInstance( this );

        Thread.currentThread().setName( "JukeboxMC Main-Thread" );
        this.mainThread = Thread.currentThread();
        this.logger = logger;

        this.pluginFolder = new File( "./plugins" );
        if ( !this.pluginFolder.exists() ) {
            this.pluginFolder.mkdirs();
        }

        this.serverId = UUID.randomUUID().getMostSignificantBits();

        this.packetRegistry = new PacketRegistry();

        this.initServerConfig();
        this.initOperatorConfig();

        this.consoleSender = new ConsoleSender( this );
        this.terminalConsole = new TerminalConsole( this );
        this.terminalConsole.startConsole();

        this.scheduler = new Scheduler( this );

        INITIATING.set( true );
        BedrockResourceLoader.init();
        BlockPalette.init();
        ItemType.init();
        for ( BlockType blockType : BlockType.values() ) {
            blockType.getBlock();
        }
        INITIATING.set( false );

        this.resourcePackManager = new ResourcePackManager();
        this.resourcePackManager.loadResourcePacks();

        this.pluginManager = new PluginManager( this );
        this.pluginManager.enableAllPlugins( PluginLoadOrder.STARTUP );

        this.registerGenerator( "Empty", EmptyGenerator.class );
        this.registerGenerator( "Flat", FlatGenerator.class );

        this.overWorldGenerator = this.worldGenerator.get( this.serverConfig.getString( "generator" ) );


        String defaultWorldName = this.getDefaultWorldName();
        if ( this.loadOrCreateWorld( defaultWorldName ) ) {
            this.defaultWorld = this.getWorld( defaultWorldName );
        }

        this.pluginManager.enableAllPlugins( PluginLoadOrder.POSTWORLD );

        this.rakNetListener = new RakNetListener( this.serverId );
        this.rakNetListener.bind();

        Runtime.getRuntime().addShutdownHook( new Thread( this::shutdown ) );

        long tpsTimeMillisStarted = System.currentTimeMillis();
        long tpsTickStarted = this.currentTick;
        long deltaTime = 50;
        while ( this.running ) {
            this.currentTick++;
            Thread.sleep( FastMath.max( 0, 50 - deltaTime ) );
            final long startTime = System.currentTimeMillis();
            while ( !this.mainThreadWork.isEmpty() ) {
                Runnable runnable = this.mainThreadWork.poll();
                if ( runnable != null ) {
                    runnable.run();
                }
            }

            this.scheduler.onTick( this.currentTick );

            for ( Player player : this.players.values() ) {
                player.updateChunks( this.currentTick );
            }

            for ( World world : this.worlds.values() ) {
                if ( world != null ) {
                    world.update( this.currentTick );
                }
            }

            if ( System.currentTimeMillis() >= tpsTimeMillisStarted + TimeUnit.SECONDS.toMillis( 1 ) ) {
                this.currentTps = this.currentTick - tpsTickStarted;
                if ( this.currentTps > 20 ) {
                    this.currentTps = 20;
                }

                tpsTickStarted = this.currentTick;
                tpsTimeMillisStarted = System.currentTimeMillis();
            }

            deltaTime = System.currentTimeMillis() - startTime;
        }
    }

    private void initServerConfig() {
        this.serverConfig = new Config( new File( System.getProperty( "user.dir" ), "properties.json" ), ConfigType.JSON );
        this.serverConfig.addDefault( "address", "0.0.0.0" );
        this.serverConfig.addDefault( "port", 19132 );
        this.serverConfig.addDefault( "maxplayers", 20 );
        this.serverConfig.addDefault( "viewdistance", 32 );
        this.serverConfig.addDefault( "motd", "§bJukeboxMC" );
        this.serverConfig.addDefault( "subMotd", "A fresh JukeboxMC Server" );
        this.serverConfig.addDefault( "gamemode", GameMode.CREATIVE.name() );
        this.serverConfig.addDefault( "defaultworld", "world" );
        this.serverConfig.addDefault( "generator", "flat" );
        this.serverConfig.addDefault( "online-mode", true );
        this.serverConfig.addDefault( "spawn-protection", true );
        this.serverConfig.addDefault( "spawn-protection-radius", 16 );
        this.serverConfig.addDefault( "forceResourcePacks", false );
        this.serverConfig.save();

        this.motd = this.serverConfig.getString( "motd" );
        this.subMotd = this.serverConfig.getString( "subMotd" );
        this.defaultGameMode = GameMode.valueOf( this.serverConfig.getString( "gamemode" ) );
        this.address = new InetSocketAddress( this.serverConfig.getString( "address" ), this.serverConfig.getInt( "port" ) );
    }

    private void initOperatorConfig() {
        this.operatorConfig = new Config( new File( System.getProperty( "user.dir" ), "operators.json" ), ConfigType.JSON );
        this.operatorConfig.addDefault( "operators", new ArrayList<String>() );
        this.operatorConfig.save();
    }

    public void shutdown() {
        if ( this.running ) {
            this.running = false;
            this.logger.info( "Shutdown server..." );

            for ( Player player : this.getOnlinePlayers() ) {
                player.disconnect( "Server shutdown" );
            }


            this.logger.info( "Unload all worlds..." );
            for ( World world : this.getWorlds() ) {
                this.unloadWorld( world.getName() );
            }

            this.logger.info( "Disabling all plugins..." );
            this.pluginManager.disableAllPlugins();

            this.terminalConsole.stopConsole();
            this.scheduler.shutdown();
            this.rakNetListener.close();
            this.logger.info( "Shutdown successfully" );
            System.exit( 0 );
        }

    }

    public boolean isMainThread() {
        return this.mainThread == Thread.currentThread();
    }

    public void addToMainThread( Runnable runnable ) {
        this.mainThreadWork.offer( runnable );
    }

    public boolean isRunning() {
        return this.running;
    }

    public double getCurrentTps() {
        return this.currentTps;
    }

    public long getCurrentTick() {
        return this.currentTick;
    }

    public static Server getInstance() {
        return instance;
    }

    public static void setInstance( Server instance ) {
        Server.instance = instance;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public long getServerId() {
        return this.serverId;
    }

    public PacketRegistry getPacketRegistry() {
        return this.packetRegistry;
    }

    public ResourcePackManager getResourcePackManager() {
        return this.resourcePackManager;
    }

    public File getPluginFolder() {
        return this.pluginFolder;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ConsoleSender getConsoleSender() {
        return this.consoleSender;
    }

    public Config getServerConfig() {
        return this.serverConfig;
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.serverConfig.getInt( "port" );
    }

    public int getMaxPlayers() {
        return this.serverConfig.getInt( "maxplayers" );
    }

    public int getViewDistance() {
        return this.serverConfig.getInt( "viewdistance" );
    }

    public String getMotd() {
        return this.motd;
    }

    public void setMotd( String motd ) {
        this.motd = motd;
    }

    public String getSubMotd() {
        return this.subMotd;
    }

    public void setSubMotd( String subMotd ) {
        this.subMotd = subMotd;
    }

    public GameMode getDefaultGameMode() {
        return this.defaultGameMode;
    }

    public void setDefaultGameMode( GameMode defaultGameMode ) {
        this.defaultGameMode = defaultGameMode;
    }

    public String getDefaultWorldName() {
        return this.serverConfig.getString( "defaultworld" );
    }

    public String getGeneratorName() {
        return this.serverConfig.getString( "generator" );
    }

    public boolean isOnlineMode() {
        return this.serverConfig.getBoolean( "online-mode" );
    }

    public boolean hasSpawnProtection() {
        return this.serverConfig.getBoolean( "spawn-protection" );
    }

    public int getSpawnProtectionRadius() {
        return this.serverConfig.getInt( "spawn-protection-radius" );
    }

    public boolean isForceResourcePacks() {
        return this.serverConfig.getBoolean( "forceResourcePacks" );
    }

    public boolean isOperatorInFile( String playerName ) {
        return this.operatorConfig.exists( "operators" ) && this.operatorConfig.getStringList( "operators" ).contains( playerName );
    }

    public void addOperatorToFile( String playerName ) {
        if ( this.operatorConfig.exists( "operators" ) && !this.operatorConfig.getStringList( "operators" ).contains( playerName ) ) {
            List<String> operators = this.operatorConfig.getStringList( "operators" );
            operators.add( playerName );
            this.operatorConfig.set( "operators", operators );
            this.operatorConfig.save();
        }
    }

    public void removeOperatorFromFile( String playerName ) {
        if ( this.operatorConfig.exists( "operators" ) && this.operatorConfig.getStringList( "operators" ).contains( playerName ) ) {
            List<String> operators = this.operatorConfig.getStringList( "operators" );
            operators.remove( playerName );
            this.operatorConfig.set( "operators", operators );
            this.operatorConfig.save();
        }
    }

    public void dispatchCommand( CommandSender commandSender, String command ) {
        this.pluginManager.getCommandManager().handleCommandInput( commandSender, "/" + command );
    }

    public boolean isInitiating() {
        return INITIATING.get();
    }

    // =========== Player ===========

    public Collection<Player> getOnlinePlayers() {
        return this.players.values();
    }

    public void addPlayer( Player player ) {
        this.players.put( player.getAddress(), player );
    }

    public void removePlayer( Player player ) {
        this.players.remove( player.getAddress() );
    }

    public Player getPlayer( InetSocketAddress socketAddress ) {
        return this.players.get( socketAddress );
    }

    public Player getPlayer( String name ) {
        for ( Player player : this.players.values() ) {
            if ( player.getName().equalsIgnoreCase( name ) ) {
                return player;
            }
        }
        return null;
    }

    public void broadcastMessage( String message ) {
        for ( Player onlinePlayers : this.players.values() ) {
            onlinePlayers.sendMessage( message );
        }
        this.logger.info( message );
    }

    public void broadcastPacket( Packet packet ) {
        for ( Player player : this.players.values() ) {
            player.sendPacket( packet );
        }
    }

    public void broadcastPacket( Set<Player> players, Packet packet ) {
        for ( Player player : players ) {
            player.sendPacket( packet );
        }
    }

    public Map<UUID, TablistEntry> getPlayerListEntry() {
        return this.playerListEntry;
    }

    public void addToTablist( Player player ) {
        this.addToTablist( player.getUUID(), player.getEntityId(), player.getName(), player.getDeviceInfo(), player.getXuid(), player.getSkin() );
    }

    public void addToTablist( UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xuid, Skin skin ) {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setType( TablistType.ADD );
        TablistEntry playerListEntry = new TablistEntry(
                uuid,
                entityId,
                name,
                deviceInfo,
                xuid,
                skin
        );
        playerListPacket.getEntries().add( playerListEntry );

        this.playerListEntry.put( uuid, playerListEntry );
        this.broadcastPacket( playerListPacket );
    }

    public void removeFromTablist( UUID uuid ) {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setType( TablistType.REMOVE );
        playerListPacket.getEntries().add( new TablistEntry( uuid ) );
        this.playerListEntry.remove( uuid );
        this.broadcastPacket( playerListPacket );
    }

    // =========== World ===========

    public Collection<World> getWorlds() {
        return this.worlds.values();
    }

    public World getDefaultWorld() {
        return this.defaultWorld;
    }

    public World getWorld( String name ) {
        if ( !this.isWorldLoaded( name ) ) {
            if ( this.loadOrCreateWorld( name ) ) {
                return this.worlds.get( name.toLowerCase() );
            }
        }
        for ( World world : this.worlds.values() ) {
            if ( world.getName().equalsIgnoreCase( name ) ) {
                return world;
            }
        }
        return null;
    }

    public boolean isWorldLoaded( String name ) {
        return this.worlds.containsKey( name.toLowerCase() );
    }

    public WorldGenerator getOverworldGenerator() {
        return this.overWorldGenerator;
    }

    public void registerGenerator( String name, Class<? extends WorldGenerator> clazz ) {
        if ( !this.worldGenerator.containsKey( name ) ) {
            try {
                this.worldGenerator.put( name.toLowerCase(), clazz.newInstance() );
            } catch ( InstantiationException | IllegalAccessException e ) {
                e.printStackTrace();
            }
        }
    }

    public boolean loadOrCreateWorld( String worldName ) {
        return this.loadOrCreateWorld( worldName, this.overWorldGenerator );
    }

    public boolean loadOrCreateWorld( String worldName, WorldGenerator worldGenerator ) {
        if ( !this.worlds.containsKey( worldName.toLowerCase() ) ) {
            File file = new File( "./worlds", worldName );
            boolean worldExists = file.exists();

            World world = new World( worldName, this, worldGenerator );
            WorldLoadEvent worldLoadEvent = new WorldLoadEvent( world, false, worldExists ? WorldLoadEvent.LoadType.LOAD : WorldLoadEvent.LoadType.CREATE );
            this.pluginManager.callEvent( worldLoadEvent );
            if ( worldLoadEvent.isCancelled() ) {
                return false;
            }

            if ( worldLoadEvent.getWorld().open() ) {
                if ( worldLoadEvent.isPrepareWorld() ) {
                    worldLoadEvent.getWorld().prepareSpawnRegion();
                }
                this.worlds.put( worldName.toLowerCase(), worldLoadEvent.getWorld() );
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
                    player.teleport( this.defaultWorld.getSpawnLocation( Dimension.OVERWORLD ) );
                }
            }
        } );
    }

    public void unloadWorld( String worldName, Consumer<Player> consumer ) {
        World world = this.getWorld( worldName );
        WorldUnloadEvent unloadWorldEvent = new WorldUnloadEvent( world );
        this.pluginManager.callEvent( unloadWorldEvent );

        if ( unloadWorldEvent.isCancelled() ) {
            return;
        }

        if ( unloadWorldEvent.getWorld() != null ) {
            for ( Player player : unloadWorldEvent.getWorld().getPlayers() ) {
                consumer.accept( player );
            }
            unloadWorldEvent.getWorld().close();
            unloadWorldEvent.getWorld().clearChunks();
            this.logger.info( "World \"" + worldName + "\" was unloaded" );
        } else {
            this.logger.warn( "The world \"" + worldName + "\" was not found" );
        }
    }

}
