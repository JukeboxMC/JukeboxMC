package org.jukeboxmc;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jukeboxmc.block.BlockRegistry;
import org.jukeboxmc.blockentity.BlockEntityRegistry;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.console.ConsoleSender;
import org.jukeboxmc.console.TerminalConsole;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.entity.EntityRegistry;
import org.jukeboxmc.event.world.WorldLoadEvent;
import org.jukeboxmc.event.world.WorldUnloadEvent;
import org.jukeboxmc.item.ItemRegistry;
import org.jukeboxmc.item.enchantment.EnchantmentRegistry;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.network.handler.HandlerRegistry;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.plugin.PluginLoadOrder;
import org.jukeboxmc.plugin.PluginManager;
import org.jukeboxmc.potion.EffectRegistry;
import org.jukeboxmc.resourcepack.ResourcePackManager;
import org.jukeboxmc.scheduler.Scheduler;
import org.jukeboxmc.util.*;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.FlatGenerator;
import org.jukeboxmc.world.generator.Generator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Server {

    private static final long TICKS = 20;
    private static Server instance;

    private final long startTime;
    private final AtomicBoolean finishedState;
    private final AtomicBoolean runningState;

    private final Thread mainThread;
    private final Logger logger;
    private final Network network;
    private final Scheduler scheduler;
    private final ResourcePackManager resourcePackManager;
    private final ConsoleSender consoleSender;
    private final TerminalConsole terminalConsole;
    private final PluginManager pluginManager;
    private final CraftingManager craftingManager;

    private Config operatorConfig;
    private final File pluginFolder;

    private String serverAddress;
    private int port;
    private int maxPlayers;
    private int viewDistance;
    private String motd;
    private String subMotd;
    private GameMode gameMode;
    private Difficulty difficulty;
    private String defaultWorldName;
    private String generatorName;
    private boolean onlineMode;
    private boolean forceResourcePacks;
    private PacketCompressionAlgorithm compressionAlgorithm;

    private final World defaultWorld;

    private final Set<Player> players = new HashSet<>();
    private final Map<String, World> worlds = new HashMap<>();
    private final Map<Dimension, String> defaultGenerators = new EnumMap<>( Dimension.class );
    private final Object2ObjectMap<UUID, PlayerListPacket.Entry> playerListEntry = new Object2ObjectOpenHashMap<>();
    private final Map<Dimension, Object2ObjectMap<String, Class<? extends Generator>>> generators = new EnumMap<>( Dimension.class );

    private long currentTick;
    private long currentTps;
    private long nextTickTime;
    private long internalDiffTime;
    private long sleepBalance;

    public Server( Logger logger ) {
        instance = this;
        JukeboxMC.setServer( this );
        this.logger = logger;
        this.startTime = System.currentTimeMillis();
        Thread.currentThread().setName( "JukeboxMC-Main" );
        this.mainThread = Thread.currentThread();

        this.finishedState = new AtomicBoolean( false );
        this.runningState = new AtomicBoolean( true );

        this.currentTps = 20;
        this.currentTick = 0;

        this.initServerConfig();
        this.initOperatorConfig();

        this.consoleSender = new ConsoleSender( this );
        this.terminalConsole = new TerminalConsole( this );
        this.terminalConsole.startConsole();

        HandlerRegistry.init();
        ItemPalette.init();
        ItemRegistry.init();
        ItemRegistry.initItemProperties();
        IdentifierMapping.init();
        BlockRegistry.init();
        BlockRegistry.initBlockProperties();
        BlockPalette.init();
        CreativeItems.init();
        EntityIdentifiers.init();
        EntityRegistry.init();
        BiomeDefinitions.init();
        Biome.init();
        BlockEntityRegistry.init();
        EnchantmentRegistry.init();
        EffectRegistry.init();

        this.scheduler = new Scheduler( this );

        this.resourcePackManager = new ResourcePackManager( logger );
        this.resourcePackManager.loadResourcePacks();

        this.craftingManager = new CraftingManager();

        this.pluginFolder = new File( "./plugins" );
        if ( !this.pluginFolder.exists() ) {
            this.pluginFolder.mkdirs();
        }

        this.pluginManager = new PluginManager( this );
        this.pluginManager.enableAllPlugins( PluginLoadOrder.STARTUP );

        this.registerGenerator( "flat", FlatGenerator.class, Dimension.OVERWORLD );
        this.registerGenerator( "empty", FlatGenerator.class, Dimension.NETHER );
        this.registerGenerator( "empty", FlatGenerator.class, Dimension.THE_END );

        this.defaultWorld = this.getWorld( this.defaultWorldName );

        this.pluginManager.enableAllPlugins( PluginLoadOrder.POSTWORLD );

        this.network = new Network( this, new InetSocketAddress( this.serverAddress, this.port ) );
        this.logger.info( "JukeboxMC started in " + TimeUnit.MILLISECONDS.toSeconds( System.currentTimeMillis() - this.startTime ) + " seconds!" );

        this.finishedState.set( true );
        this.startTick();
        this.shutdown();
    }

    private void startTick() {
        this.nextTickTime = System.currentTimeMillis();

        try {
            while ( this.runningState.get() ) {
                final long startTimeMillis = System.currentTimeMillis();

                if ( this.nextTickTime - startTimeMillis > 25 ) {
                    synchronized (this) {
                        this.wait( Math.max( 5, this.nextTickTime - startTimeMillis - 25 ) );
                    }
                }

                this.tick();

                this.nextTickTime += 50;
            }
        } catch ( InterruptedException e ) {
            Logger.getInstance().error( "Error whilst waiting for next tick!", e );
        }
    }

    private void tick() {
        long skipNanos = TimeUnit.SECONDS.toNanos( 1 ) / TICKS;
        float lastTickTime;

        while ( this.runningState.get() ) {
            this.internalDiffTime = System.nanoTime();

            this.currentTick++;

            this.scheduler.onTick( this.currentTick );
            this.network.update();
            for ( World value : this.worlds.values() ) {
                value.update( this.currentTick );
            }
            if ( !this.runningState.get() ) {
                break;
            }

            long startSleep = System.nanoTime();
            long diff = startSleep - this.internalDiffTime;
            if ( diff <= skipNanos ) {
                long sleepNeeded = ( skipNanos - diff ) - this.sleepBalance;
                this.sleepBalance = 0;

                LockSupport.parkNanos( sleepNeeded );

                long endSleep = System.nanoTime();
                long sleptFor = endSleep - startSleep;
                diff = skipNanos;

                if ( sleptFor > sleepNeeded ) {
                    this.sleepBalance = sleptFor - sleepNeeded;
                }
            }

            lastTickTime = (float) diff / TimeUnit.SECONDS.toNanos( 1 );
            this.currentTps = (int) Math.round( ( 1 / (double) lastTickTime ) );
        }
    }

    public void shutdown() {
        if ( !this.runningState.get() ) {
            return;
        }
        this.logger.info( "Shutdown server..." );
        this.runningState.set( false );

        this.pluginManager.disableAllPlugins();

        this.logger.info( "Save all worlds..." );
        for ( World world : this.worlds.values() ) {
            world.saveChunks( Dimension.OVERWORLD ).join();
            world.saveChunks( Dimension.NETHER ).join();
            world.saveChunks( Dimension.THE_END ).join();
            this.logger.info( "The world \"" + world.getName() + "\" was saved!" );
        }

        this.worlds.values().forEach( World::close );

        this.terminalConsole.stopConsole();
        this.scheduler.shutdown();
        this.network.getBedrockServer().close( true );

        this.logger.info( "Stopping other threads" );
        for ( Thread thread : Thread.getAllStackTraces().keySet() ) {
            if ( thread.isAlive() ) {
                thread.interrupt();
            }
        }

        ServerKiller serverKiller = new ServerKiller( this.logger );
        serverKiller.start();
    }

    private void initServerConfig() {
        Config serverConfig = new Config( new File( System.getProperty( "user.dir" ), "properties.json" ), ConfigType.JSON );
        serverConfig.addDefault( "address", "0.0.0.0" );
        serverConfig.addDefault( "port", 19132 );
        serverConfig.addDefault( "max-players", 20 );
        serverConfig.addDefault( "view-distance", 32 );
        serverConfig.addDefault( "motd", "§bJukeboxMC" );
        serverConfig.addDefault( "sub-motd", "A fresh JukeboxMC Server" );
        serverConfig.addDefault( "gamemode", GameMode.CREATIVE.name() );
        serverConfig.addDefault( "default-difficulty", Difficulty.NORMAL.name() );
        serverConfig.addDefault( "default-world", "world" );
        serverConfig.addDefault( "generator", "flat" );
        serverConfig.addDefault( "online-mode", true );
        serverConfig.addDefault( "forceResourcePacks", false );
        serverConfig.addDefault( "compression", "snappy" );
        serverConfig.save();

        this.serverAddress = serverConfig.getString( "address" );
        this.port = serverConfig.getInt( "port" );
        this.maxPlayers = serverConfig.getInt( "max-players" );
        this.viewDistance = serverConfig.getInt( "view-distance" );
        this.motd = serverConfig.getString( "motd" );
        this.subMotd = serverConfig.getString( "sub-motd" );
        this.gameMode = GameMode.valueOf( serverConfig.getString( "gamemode" ) );
        this.difficulty = Difficulty.valueOf( serverConfig.getString( "default-difficulty" ) );
        this.defaultWorldName = serverConfig.getString( "default-world" );
        this.generatorName = serverConfig.getString( "generator" );
        this.onlineMode = serverConfig.getBoolean( "online-mode" );
        this.forceResourcePacks = serverConfig.getBoolean( "forceResourcePacks" );

        String compression = serverConfig.getString( "compression" );

        //Snappy is used as default since v554 (1.19.30)
        this.compressionAlgorithm = PacketCompressionAlgorithm.SNAPPY;

        for ( PacketCompressionAlgorithm algorithm : PacketCompressionAlgorithm.values() ) {
            if ( algorithm.name().equalsIgnoreCase( compression ) ) {
                this.compressionAlgorithm = algorithm;
                break;
            }
        }
    }

    private void initOperatorConfig() {
        this.operatorConfig = new Config( new File( System.getProperty( "user.dir" ), "operators.json" ), ConfigType.JSON );
        this.operatorConfig.addDefault( "operators", new ArrayList<String>() );
        this.operatorConfig.save();
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

    public static Server getInstance() {
        return instance;
    }

    public AtomicBoolean getFinishedState() {
        return this.finishedState;
    }

    public AtomicBoolean getRunningState() {
        return this.runningState;
    }

    public boolean isMainThread() {
        return Thread.currentThread().getId() == this.mainThread.getId();
    }

    public Thread getMainThread() {
        return this.mainThread;
    }

    public long getCurrentTick() {
        return this.currentTick;
    }

    public long getCurrentTps() {
        return this.currentTps;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public int getPort() {
        return this.port;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public String getMotd() {
        return this.motd;
    }

    public String getSubMotd() {
        return this.subMotd;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public String getDefaultWorldName() {
        return this.defaultWorldName;
    }

    public String getGeneratorName() {
        return this.generatorName;
    }

    public World getDefaultWorld() {
        return this.defaultWorld;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public boolean isForceResourcePacks() {
        return this.forceResourcePacks;
    }

    public PacketCompressionAlgorithm getCompressionAlgorithm() {
        return this.compressionAlgorithm;
    }

    public File getPluginFolder() {
        return this.pluginFolder;
    }

    public void addPlayer( Player player ) {
        this.players.add( player );
    }

    public void removePlayer( Player player ) {
        this.players.removeIf( target -> target.getUUID().equals( player.getUUID() ) );
    }

    public Collection<Player> getOnlinePlayers() {
        return this.players;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public World getWorld( String name ) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        name = name.toLowerCase();
        if ( this.worlds.containsKey( name ) ) {
            return this.worlds.get( name );
        }
        Map<Dimension, String> generatorMap = new EnumMap<>( Dimension.class );
        generatorMap.put( Dimension.OVERWORLD, this.generatorName );
        for ( Dimension dimension : Dimension.values() ) {
            generatorMap.putIfAbsent( dimension, this.defaultGenerators.get( dimension ) );
        }
        return this.loadWorld( name, generatorMap );
    }

    public World loadWorld( String name ) {
        Map<Dimension, String> generatorMap = new EnumMap<>( Dimension.class );
        generatorMap.put( Dimension.OVERWORLD, this.generatorName );
        for ( Dimension dimension : Dimension.values() ) {
            generatorMap.putIfAbsent( dimension, this.defaultGenerators.get( dimension ) );
        }
        return this.loadWorld( name, generatorMap );
    }

    public World loadWorld( String name, Map<Dimension, String> generatorMap ) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        File file = new File( "./worlds", name );
        boolean worldExists = file.exists();
        name = name.toLowerCase();

        if ( this.worlds.containsKey( name ) ) {
            return this.worlds.get( name );
        }

        World world = new World( name, this, generatorMap );
        WorldLoadEvent worldLoadEvent = new WorldLoadEvent( world, worldExists ? WorldLoadEvent.LoadType.LOAD : WorldLoadEvent.LoadType.CREATE );
        this.pluginManager.callEvent( worldLoadEvent );
        if ( worldLoadEvent.isCancelled() ) {
            return null;
        }
        if ( !this.worlds.containsKey( name ) ) {
            this.worlds.put( name, world );
            return world;
        }
        return null;
    }

    public void unloadWorld( String worldName ) {
        this.unloadWorld( worldName, player -> {
            World world = this.getWorld( worldName );
            if ( world != null ) {
                if ( world == this.defaultWorld || this.defaultWorld == null ) {
                    player.getPlayerConnection().disconnect( "World was unloaded" );
                } else {
                    player.teleport( this.defaultWorld.getSpawnLocation() );
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
            this.worlds.remove( worldName.toLowerCase() );
            this.logger.info( "World \"" + worldName + "\" was unloaded" );
        } else {
            this.logger.warn( "The world \"" + worldName + "\" was not found" );
        }
    }

    public Collection<World> getWorlds() {
        return this.worlds.values();
    }

    public boolean isWorldLoaded( String name ) {
        return this.worlds.containsKey( name.toLowerCase() );
    }

    public synchronized String getDefaultGenerator( Dimension dimension ) {
        return this.defaultGenerators.get( dimension );
    }

    public synchronized Generator createGenerator( String generatorName, Dimension dimension ) {
        Object2ObjectMap<String, Class<? extends Generator>> generators = this.generators.get( dimension );

        Class<? extends Generator> generator = generators.get( generatorName.toLowerCase() );
        if ( generator != null ) {
            try {
                return generator.getConstructor().newInstance();
            } catch ( InvocationTargetException | InstantiationException | IllegalAccessException |
                      NoSuchMethodException e ) {
                throw new RuntimeException( e );
            }
        }
        return null;
    }

    public void registerGenerator( String name, Class<? extends Generator> clazz, Dimension... dimensions ) {
        name = name.toLowerCase();
        for ( Dimension dimension : dimensions ) {
            Object2ObjectMap<String, Class<? extends Generator>> generators = this.generators.computeIfAbsent( dimension, k -> new Object2ObjectOpenHashMap<>() );
            if ( !generators.containsKey( name ) ) {
                generators.put( name, clazz );
            }
        }
    }

    public ResourcePackManager getResourcePackManager() {
        return this.resourcePackManager;
    }

    public CraftingManager getCraftingManager() {
        return this.craftingManager;
    }

    public ConsoleSender getConsoleSender() {
        return this.consoleSender;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public void addToTabList( Player player ) {
        this.addToTabList( player.getUUID(), player.getEntityId(), player.getName(), player.getDeviceInfo(), player.getXuid(), player.getSkin() );
    }

    public void addToTabList( UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xuid, Skin skin ) {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setAction( PlayerListPacket.Action.ADD );
        PlayerListPacket.Entry entry = new PlayerListPacket.Entry( uuid );
        entry.setEntityId( entityId );
        entry.setName( name );
        entry.setXuid( xuid );
        entry.setPlatformChatId( deviceInfo.getDeviceName() );
        entry.setBuildPlatform( deviceInfo.getDevice().getId() );
        entry.setSkin( skin.toNetwork() );
        playerListPacket.getEntries().add( entry );
        this.playerListEntry.put( uuid, entry );
        this.broadcastPacket( playerListPacket );
    }

    public void removeFromTabList( Player player ) {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setAction( PlayerListPacket.Action.REMOVE );
        playerListPacket.getEntries().add( new PlayerListPacket.Entry( player.getUUID() ) );
        this.broadcastPacket( playerListPacket );
        this.playerListEntry.remove( player.getUUID() );
    }

    public Object2ObjectMap<UUID, PlayerListPacket.Entry> getPlayerListEntry() {
        return this.playerListEntry;
    }

    public void broadcastPacket( BedrockPacket packet ) {
        this.players.forEach( player -> player.getPlayerConnection().sendPacket( packet ) );
    }

    public void broadcastPacket( Set<Player> players, BedrockPacket packet ) {
        players.forEach( player -> player.getPlayerConnection().sendPacket( packet ) );
    }

    public void broadcastMessage( String message ) {
        for ( Player player : this.getOnlinePlayers() ) {
            player.sendMessage( message );
        }
        this.logger.info( message );
    }

    public Player getPlayer( String playerName ) {
        for ( Player player : new ArrayList<>( this.players ) ) {
            if ( player.getName().equalsIgnoreCase( playerName ) ) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayer( UUID uuid ) {
        for ( Player player : new ArrayList<>( this.players ) ) {
            if ( player.getUUID().equals( uuid ) ) {
                return player;
            }
        }
        return null;
    }

    public void dispatchCommand( CommandSender commandSender, String command ) {
        this.pluginManager.getCommandManager().handleCommandInput( commandSender, "/" + command );
    }
}
