package org.jukeboxmc;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.console.ConsoleSender;
import org.jukeboxmc.console.TerminalConsole;
import org.jukeboxmc.crafting.CraftingManager;
import org.jukeboxmc.event.world.WorldLoadEvent;
import org.jukeboxmc.event.world.WorldUnloadEvent;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.network.handler.HandlerRegistry;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.plugin.PluginLoadOrder;
import org.jukeboxmc.plugin.PluginManager;
import org.jukeboxmc.resourcepack.ResourcePackManager;
import org.jukeboxmc.scheduler.Scheduler;
import org.jukeboxmc.util.BiomeDefinitions;
import org.jukeboxmc.util.CreativeItems;
import org.jukeboxmc.util.EntityIdentifiers;
import org.jukeboxmc.util.ItemPalette;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.EmptyGenerator;
import org.jukeboxmc.world.generator.FlatGenerator;
import org.jukeboxmc.world.generator.Generator;
import org.jukeboxmc.world.generator.NormalGenerator;
import org.jukeboxmc.world.generator.biome.BiomeGenerationRegistry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Server {

    private static Server instance;

    private final AtomicBoolean runningState;
    private final int ticks = 20;
    private final long startTime;
    private long currentTick;
    private long currentTps;
    private long tickTpsStarted;
    private long timeMillisTpsStarted;
    private final long[] ticksAverage;

    private final boolean WARN_IF_TICKS_BEHIND = false;
    private final long TICK_NS = TimeUnit.SECONDS.toNanos( 1 ) / this.ticks;
    private final long TICK_MS = TimeUnit.SECONDS.toMillis( 1 ) / this.ticks;

    private final Logger logger;
    private final Scheduler scheduler;
    private final Network network;
    private final ResourcePackManager resourcePackManager;
    private World defaultWorld;
    private final PluginManager pluginManager;
    private final ConsoleSender consoleSender;
    private final TerminalConsole terminalConsole;
    private final CraftingManager craftingManager;

    private final File pluginFolder;
    private final File playersFolder;

    private Config operatorConfig;

    private String serverAddress;
    private int port;
    private int maxPlayers;
    private int viewdistance;
    private String motd;
    private String subMotd;
    private GameMode gameMode;
    private String defaultWorldName;
    private String generatorName;
    private boolean onlineMode;
    private boolean forceResourcePacks;


    private static final AtomicBoolean INITIATING = new AtomicBoolean( true );

    private final Set<Player> players = new HashSet<>();
    private final Object2ObjectMap<String, World> worlds = new Object2ObjectOpenHashMap<>();
    private final Map<Dimension, Object2ObjectMap<String, Class<? extends Generator>>> generators = new EnumMap<>( Dimension.class );
    private final Map<Dimension, String> defaultGenerators = new EnumMap<>( Dimension.class );
    private final Object2ObjectMap<UUID, PlayerListPacket.Entry> playerListEntry = new Object2ObjectOpenHashMap<>();

    public Server( Logger logger ) {
        instance = this;
        JukeboxMC.setServer( this );
        this.startTime = System.currentTimeMillis();
        Thread.currentThread().setName( "JukeboxMC-Main" );

        this.runningState = new AtomicBoolean( true );

        this.currentTick = 0;
        this.currentTps = this.ticks;
        this.tickTpsStarted = 0;
        this.timeMillisTpsStarted = this.startTime;
        Arrays.fill( this.ticksAverage = new long[20], this.ticks );

        this.logger = logger;

        this.initServerConfig();
        this.initOperatorConfig();
        HandlerRegistry.init();

        this.consoleSender = new ConsoleSender( this );
        this.terminalConsole = new TerminalConsole( this );
        this.terminalConsole.startConsole();

        this.scheduler = new Scheduler( this );

        this.network = new Network( this, new InetSocketAddress( this.serverAddress, this.port ) );

        INITIATING.set( true );
        BlockPalette.init();
        ItemPalette.init();
        CreativeItems.init();
        BiomeDefinitions.init();
        EntityIdentifiers.init();

        ItemType.init();
        BlockType.init();
        Biome.init();
        BiomeGenerationRegistry.init();
        INITIATING.set( false );

        this.resourcePackManager = new ResourcePackManager( logger );
        this.resourcePackManager.loadResourcePacks();

        this.craftingManager = new CraftingManager();

        this.pluginFolder = new File( "./plugins" );
        if ( !this.pluginFolder.exists() ) {
            this.pluginFolder.mkdirs();
        }

        this.playersFolder = new File( "./players" );
        if ( !this.playersFolder.exists() ) {
            this.playersFolder.mkdirs();
        }

        this.pluginManager = new PluginManager( this );
        this.pluginManager.enableAllPlugins( PluginLoadOrder.STARTUP );

        this.registerDefaultGenerator( Dimension.OVERWORLD, "empty", EmptyGenerator.class );
        this.registerDefaultGenerator( Dimension.NETHER, "empty", EmptyGenerator.class );
        this.registerDefaultGenerator( Dimension.THE_END, "empty", EmptyGenerator.class );

        this.registerGenerator( "flat", FlatGenerator.class, Dimension.OVERWORLD, Dimension.NETHER, Dimension.THE_END );
        this.registerGenerator( "normal", NormalGenerator.class, Dimension.OVERWORLD );

        String defaultWorldName = this.defaultWorldName;
        if ( this.loadOrCreateWorld( defaultWorldName ) ) {
            this.defaultWorld = this.getWorld( defaultWorldName );
        }
        this.pluginManager.enableAllPlugins( PluginLoadOrder.POSTWORLD );

        this.network.start();
        this.tick();
        this.shutdown();
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
        serverConfig.addDefault( "default-world", "world" );
        serverConfig.addDefault( "generator", "flat" ); //TODO
        serverConfig.addDefault( "online-mode", true );
        serverConfig.addDefault( "forceResourcePacks", false );
        serverConfig.save();

        this.serverAddress = serverConfig.getString( "address" );
        this.port = serverConfig.getInt( "port" );
        this.maxPlayers = serverConfig.getInt( "max-players" );
        this.viewdistance = serverConfig.getInt( "view-distance" );
        this.motd = serverConfig.getString( "motd" );
        this.subMotd = serverConfig.getString( "sub-motd" );
        this.gameMode = GameMode.valueOf( serverConfig.getString( "gamemode" ) );
        this.defaultWorldName = serverConfig.getString( "default-world" );
        this.generatorName = serverConfig.getString( "generator" );
        this.onlineMode = serverConfig.getBoolean( "online-mode" );
        this.forceResourcePacks = serverConfig.getBoolean( "forceResourcePacks" );
    }

    private void initOperatorConfig() {
        this.operatorConfig = new Config( new File( System.getProperty( "user.dir" ), "operators.json" ), ConfigType.JSON );
        this.operatorConfig.addDefault( "operators", new ArrayList<String>() );
        this.operatorConfig.save();
    }

    public void shutdown() {
        if ( !this.runningState.get() ) {
            return;
        }
        this.logger.info( "Shutdown server..." );
        this.runningState.set( false );

        this.logger.info( "Save all worlds..." );
        for ( World world : this.worlds.values() ) {
            world.save();
        }

        this.logger.info( "Unload all worlds..." );
        for ( World world : this.worlds.values() ) {
            this.unloadWorld( world.getName() );
            this.logger.info( "World " + world.getName() + " was unloaded." );
        }

        this.logger.info( "Disable plugins..." );
        this.pluginManager.disableAllPlugins();

        this.terminalConsole.stopConsole();

        this.network.getBedrockServer().close( true );
        this.scheduler.shutdown();
        this.logger.info( "Server shutdown successfully!" );
        System.exit( -1 );
    }

    private void tick() {
        while ( this.runningState.get() ) {
            try {
                long timeMillis = System.currentTimeMillis();
                long nanoStart = System.nanoTime();

                this.currentTick++;

                this.scheduler.onTick( this.currentTick );
                this.network.update();

                for ( World world : this.worlds.values() ) {
                    if ( world != null ) {
                        world.update( this.currentTick );
                    }
                }

                long nanoDiff = System.nanoTime() - nanoStart;
                long timeMillisDiff = timeMillis - this.timeMillisTpsStarted;

                if ( timeMillisDiff >= TimeUnit.SECONDS.toMillis( 1 ) ) {
                    long currentTps = this.currentTick - this.tickTpsStarted;

                    System.arraycopy( this.ticksAverage, 1, this.ticksAverage, 0, this.ticksAverage.length - 1 );
                    this.ticksAverage[this.ticksAverage.length - 1] = currentTps;

                    this.currentTps = currentTps;
                    this.timeMillisTpsStarted = System.currentTimeMillis();
                    this.tickTpsStarted = this.currentTick;
                }

                if ( nanoDiff > TICK_NS ) {
                    if ( !WARN_IF_TICKS_BEHIND ) continue;

                    final int ticksBehind = (int) ( nanoDiff / TICK_NS );
                    final long milliDiff = TimeUnit.NANOSECONDS.toMillis( nanoDiff );
                    if ( ticksBehind == 1 ) {
                        Logger.getInstance().warn( "Server is running 1 tick or " + ( milliDiff - TICK_MS ) + "ms behind!" );
                    } else if ( ticksBehind > 1 ) {
                        Logger.getInstance().warn( "Server is running " + ticksBehind + " ticks or " + ( milliDiff - TICK_MS ) + "ms behind!" );
                    }
                    continue;
                }

                final long diffNs = TICK_NS - nanoDiff;
                final long diffMs = TimeUnit.NANOSECONDS.toMillis( diffNs );
                if ( diffMs > 0 ) {
                    synchronized (this) {
                        try {
                            this.wait( diffMs, (int) ( diffNs - TimeUnit.MILLISECONDS.toNanos( diffMs ) ) );
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            } catch ( Throwable throwable ) {
                Logger.getInstance().error( "Error whilst ticking server!", throwable );
            }
        }
    }

    public void broadcastPacket( BedrockPacket packet ) {
        for ( Player player : this.players ) {
            player.sendPacket( packet );
        }
    }

    public void broadcastPacket( Collection<Player> players, BedrockPacket packet ) {
        for ( Player player : players ) {
            player.sendPacket( packet );
        }
    }

    public void addToTablist( Player player ) {
        this.addToTablist( player.getUUID(), player.getEntityId(), player.getName(), player.getDeviceInfo(), player.getXuid(), player.getSkin() );
    }

    public void addToTablist( UUID uuid, long entityId, String name, DeviceInfo deviceInfo, String xuid, Skin skin ) {
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

    public void removeFromTablist( Player player ) {
        PlayerListPacket playerListPacket = new PlayerListPacket();
        playerListPacket.setAction( PlayerListPacket.Action.REMOVE );
        player.sendPacket( playerListPacket );
        this.playerListEntry.remove( player.getUUID() );
    }

    public Object2ObjectMap<UUID, PlayerListPacket.Entry> getPlayerListEntry() {
        return this.playerListEntry;
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

    public synchronized void registerDefaultGenerator( Dimension dimension, String name, Class<? extends Generator> clazz ) {
        this.defaultGenerators.put( dimension, name );

        Object2ObjectMap<String, Class<? extends Generator>> generators = this.generators.computeIfAbsent( dimension, k -> new Object2ObjectOpenHashMap<>() );
        if ( !generators.containsKey( name ) ) {
            generators.put( name.toLowerCase(), clazz );
        }
    }

    public synchronized void registerGenerator( String name, Class<? extends Generator> clazz, Dimension... dimensions ) {
        for ( Dimension dimension : dimensions ) {
            Object2ObjectMap<String, Class<? extends Generator>> generators = this.generators.computeIfAbsent( dimension, k -> new Object2ObjectOpenHashMap<>() );
            if ( !generators.containsKey( name ) ) {
                generators.put( name.toLowerCase(), clazz );
            }
        }
    }

    public synchronized String getDefaultGenerator( Dimension dimension ) {
        return this.defaultGenerators.get( dimension );
    }

    public synchronized Generator createGenerator( World world, String generatorName, Dimension dimension ) {
        Object2ObjectMap<String, Class<? extends Generator>> generators = this.generators.get( dimension );

        Class<? extends Generator> generator = generators.get( generatorName.toLowerCase() );
        if ( generator != null ) {
            try {
                return generator.getConstructor( World.class, Dimension.class ).newInstance( world, dimension );
            } catch ( InvocationTargetException | InstantiationException | IllegalAccessException |
                      NoSuchMethodException e ) {
                throw new RuntimeException( e );
            }
        }

        String defaultGeneratorName = this.defaultGenerators.get( dimension );

        if ( defaultGeneratorName == null ) {
            throw new RuntimeException( "Could not find default generator for dimension " + dimension.name() );
        }

        generator = generators.get( defaultGeneratorName.toLowerCase() );
        if ( generator != null ) {
            try {
                return generator.getConstructor( World.class, Dimension.class ).newInstance( world, dimension );
            } catch ( InvocationTargetException | InstantiationException | IllegalAccessException |
                      NoSuchMethodException e ) {
                throw new RuntimeException( e );
            }
        } else {
            throw new RuntimeException( "Could not find default generator for dimension " + dimension.name() );
        }
    }

    public boolean loadOrCreateWorld( String worldName ) {
        Map<Dimension, String> generatorMap = new EnumMap<>( Dimension.class );
        generatorMap.put( Dimension.OVERWORLD, this.generatorName );

        for ( Dimension dimension : Dimension.values() ) {
            generatorMap.putIfAbsent( dimension, this.defaultGenerators.get( dimension ) );
        }

        return this.loadOrCreateWorld( worldName, generatorMap );
    }

    public boolean loadOrCreateWorld( String worldName, Map<Dimension, String> generatorMap  ) {
        if ( !this.worlds.containsKey( worldName.toLowerCase() ) ) {
            File file = new File( "./worlds", worldName );
            boolean worldExists = file.exists();

            World world = new World( worldName, this, generatorMap );
            WorldLoadEvent worldLoadEvent = new WorldLoadEvent( world, worldExists ? WorldLoadEvent.LoadType.LOAD : WorldLoadEvent.LoadType.CREATE );
            this.pluginManager.callEvent( worldLoadEvent );
            if ( worldLoadEvent.isCancelled() ) {
                return false;
            }

            if ( worldLoadEvent.getWorld().open() ) {
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
            unloadWorldEvent.getWorld().clearChunks();
            this.logger.info( "World \"" + worldName + "\" was unloaded" );
        } else {
            this.logger.warn( "The world \"" + worldName + "\" was not found" );
        }
    }

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

    public void broadcastMessage( String message ) {
        for ( Player player : this.getOnlinePlayers() ) {
            player.sendMessage( message );
        }
        this.logger.info( message );
    }

    public void dispatchCommand( CommandSender commandSender, String command ) {
        this.pluginManager.getCommandManager().handleCommandInput( commandSender, "/" + command );
    }


    public static Server getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public ResourcePackManager getResourcePackManager() {
        return this.resourcePackManager;
    }

    public long getCurrentTick() {
        return this.currentTick;
    }

    public long getCurrentTps() {
        return this.currentTps;
    }

    public float getCurrentAverageTps() {
        float sum = 0;
        int count = this.ticksAverage.length;
        for(float ticksAvg : this.ticksAverage) sum += ticksAvg;

        return (Math.round(sum * 100F / count)) / 100F;
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

    public int getViewdistance() {
        return this.viewdistance;
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

    public String getDefaultWorldName() {
        return this.defaultWorldName;
    }

    public String getGeneratorName() {
        return this.generatorName;
    }

    public boolean isOnlineMode() {
        return this.onlineMode;
    }

    public boolean isForceResourcePacks() {
        return this.forceResourcePacks;
    }

    public File getPluginFolder() {
        return this.pluginFolder;
    }

    public File getPlayersFolder() {
        return this.playersFolder;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public boolean isRunning() {
        return this.runningState.get();
    }

    public ConsoleSender getConsoleSender() {
        return this.consoleSender;
    }

    public CraftingManager getCraftingManager() {
        return this.craftingManager;
    }

    public boolean isInitiating() {
        return INITIATING.get();
    }
}
