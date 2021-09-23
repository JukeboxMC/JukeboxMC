package org.jukeboxmc.player;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.event.entity.EntityHealEvent;
import org.jukeboxmc.event.inventory.InventoryCloseEvent;
import org.jukeboxmc.event.inventory.InventoryOpenEvent;
import org.jukeboxmc.event.player.PlayerJoinEvent;
import org.jukeboxmc.event.player.PlayerQuitEvent;
import org.jukeboxmc.inventory.*;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.packet.type.PlayStatus;
import org.jukeboxmc.network.packet.type.PlayerMoveMode;
import org.jukeboxmc.network.packet.type.TablistType;
import org.jukeboxmc.network.packet.type.TextType;
import org.jukeboxmc.utils.ChunkComparator;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player extends EntityHuman implements InventoryHolder, CommandSender {

    private final PlayerConnection playerConnection;
    private final Server server;
    private Locale locale;
    private GameMode gameMode;
    private final AdventureSettings adventureSettings;
    private final ChunkComparator chunkComparator;

    private ContainerInventory currentInventory;
    private final CursorInventory cursorInventory;
    private final CraftingTableInventory craftingTableInventory;
    private final CartographyTableInventory cartographyTableInventory;
    private final SmithingTableInventory smithingTableInventory;
    private final AnvilInventory anvilInventory;
    private final EnderChestInventory enderChestInventory;
    private final StoneCutterInventory stoneCutterInventory;
    private final GrindstoneInventory grindstoneInventory;

    private int protocol = Protocol.CURRENT_PROTOCOL;
    private int viewDistance = 8;
    private int inAirTicks = 0;

    private String name = null;
    private String xuid = null;
    private String minecraftVersion = null;

    private boolean breakingBlock = false;
    private long lastBreakTime = 0;
    private Vector lasBreakPosition;

    private long actionStart = -1;

    private boolean isDead = false;

    private final Queue<Long> chunkLoadQueue = new LinkedList<>();
    private final Set<Long> loadingChunks = new HashSet<>();
    private final Set<Long> loadedChunks = new HashSet<>();
    private final Set<UUID> emotes = new HashSet<>();

    public Player( PlayerConnection playerConnection ) {
        super();
        this.playerConnection = playerConnection;
        this.server = playerConnection.getServer();
        this.gameMode = this.server.getDefaultGameMode();

        this.cursorInventory = new CursorInventory( this, this.entityId );
        this.craftingTableInventory = new CraftingTableInventory( this );
        this.cartographyTableInventory = new CartographyTableInventory( this );
        this.smithingTableInventory = new SmithingTableInventory( this );
        this.anvilInventory = new AnvilInventory( this );
        this.enderChestInventory = new EnderChestInventory( this );
        this.stoneCutterInventory = new StoneCutterInventory( this );
        this.grindstoneInventory = new GrindstoneInventory( this );

        this.adventureSettings = new AdventureSettings( this );
        this.chunkComparator = new ChunkComparator( this );

        this.lasBreakPosition = new Vector( 0, 0, 0 );
    }

    @Override
    public void update( long currentTick ) {
        if ( this.closed ) {
            return;
        }

        this.needNewChunks();

        if ( !this.chunkLoadQueue.isEmpty() ) {
            Long hash;
            while ( ( hash = this.chunkLoadQueue.poll() ) != null ) {
                int chunkX = Utils.fromHashX( hash );
                int chunkZ = Utils.fromHashZ( hash );

                World world = this.getWorld();
                world.loadChunkAsync( chunkX, chunkZ, this.dimension ).whenComplete( ( chunk, throwable ) -> {
                    this.sendChunk( chunk );
                } ).thenRun( () -> {
                    Server.getInstance().addToMainThread( this::sendNetworkChunkPublisher );
                } );
            }
        }

        this.updateAttributes();

        Collection<Entity> nearbyEntities = this.getWorld().getNearbyEntities( this.getBoundingBox().grow( 1, 0.5f, 1 ), this.dimension, null );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                nearbyEntity.onCollideWithPlayer( this );
            }
        }

        if ( !this.onGround ) {
            ++this.inAirTicks;
        } else {
            if ( this.inAirTicks > 0 ) {
                this.inAirTicks = 0;
            }
        }

        if ( !this.isDead ) {
            Attribute hungerAttribute = this.getAttribute( AttributeType.PLAYER_HUNGER );
            float hunger = hungerAttribute.getCurrentValue();
            float health = -1;
            Difficulty difficulty = this.getWorld().getDifficulty();
            if ( difficulty.equals( Difficulty.PEACEFUL ) && this.foodTicks % 10 == 0 ) {
                if ( hunger < hungerAttribute.getMaxValue() ) {
                    this.addHunger( 1 );
                }
                if ( this.foodTicks % 20 == 0 ) {
                    health = this.getHealth();
                    if ( health < this.getAttribute( AttributeType.HEALTH ).getMaxValue() ) {
                        this.setHeal( 1, EntityHealEvent.Cause.SATURATION );
                    }
                }
            }
            if ( this.foodTicks == 0 ) {
                if ( hunger >= 18 ) {
                    if ( health == -1 ) {
                        health = this.getHealth();
                    }

                    if ( health < 20 ) {
                        this.setHeal( 1, EntityHealEvent.Cause.SATURATION );
                        this.exhaust( 3 );
                    }
                } else if ( hunger <= 0 ) {
                    if ( health == -1 ) {
                        health = this.getHealth();
                    }

                    if ( ( health > 10 && difficulty.equals( Difficulty.NORMAL ) ) || ( difficulty.equals( Difficulty.HARD ) && health > 1 ) ) {
                        //TODO this.damage
                    }
                }
            }
            this.foodTicks++;
            if ( this.foodTicks >= 80 ) {
                this.foodTicks = 0;
            }
        }
    }
    // ========== Chunk ==========

    public void needNewChunks() {
        try {
            int currentXChunk = Utils.blockToChunk( this.location.getBlockX() );
            int currentZChunk = Utils.blockToChunk( this.location.getBlockZ() );
            int viewDistance = this.getViewDistance();

            List<Long> toSendChunks = new ArrayList<>();
            for ( int sendXChunk = -viewDistance; sendXChunk <= viewDistance; sendXChunk++ ) {
                for ( int sendZChunk = -viewDistance; sendZChunk <= viewDistance; sendZChunk++ ) {
                    float distance = (float) FastMath.sqrt( sendZChunk * sendZChunk + sendXChunk * sendXChunk );
                    int chunkDistance = FastMath.round( distance );

                    if ( chunkDistance <= viewDistance ) {
                        long hash = Utils.toLong( currentXChunk + sendXChunk, currentZChunk + sendZChunk );
                        if ( !this.loadedChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                            toSendChunks.add( hash );
                        }
                    }
                }
            }

            toSendChunks.sort( this.chunkComparator );

            for ( long hash : toSendChunks ) {
                this.loadingChunks.add( hash );
                this.requestChunk( Utils.fromHashX( hash ), Utils.fromHashZ( hash ) );
            }

            boolean unloaded = false;

            Iterator<Long> iterator = loadedChunks.iterator();
            while ( iterator.hasNext() ) {
                long hash = iterator.next();
                int x = Utils.fromHashX( hash );
                int z = Utils.fromHashZ( hash );

                if ( FastMath.abs( x - currentXChunk ) > viewDistance || FastMath.abs( z - currentZChunk ) > viewDistance ) {
                    unloaded = true;
                    iterator.remove();
                }
            }

            Iterator<Long> loadingIterator = this.loadingChunks.iterator();
            while ( loadingIterator.hasNext() ) {
                Long hash = loadingIterator.next();
                if ( hash != null ) {
                    int x = Utils.fromHashX( hash );
                    int z = Utils.fromHashZ( hash );

                    if ( FastMath.abs( x - currentXChunk ) > viewDistance || FastMath.abs( z - currentZChunk ) > viewDistance ) {
                        loadingIterator.remove();
                    }
                }
            }

            if ( unloaded || !this.loadingChunks.isEmpty() ) {
                this.sendNetworkChunkPublisher();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void requestChunk( int chunkX, int chunkZ ) {
        this.chunkLoadQueue.offer( Utils.toLong( chunkX, chunkZ ) );
    }

    public void resetChunks() {
        this.chunkLoadQueue.clear();
        this.loadedChunks.clear();
        this.loadingChunks.clear();
    }

    public void sendChunk( Chunk chunk ) {
        this.playerConnection.sendPacket( chunk.createLevelChunkPacket() );
        this.server.addToMainThread( () -> {
            this.loadingChunks.remove( chunk.toChunkHash() );
            this.loadedChunks.add( chunk.toChunkHash() );
        } );
    }

    public void sendNetworkChunkPublisher() {
        NetworkChunkPublisherUpdatePacket publisherUpdatePacket = new NetworkChunkPublisherUpdatePacket();
        publisherUpdatePacket.setX( this.location.getBlockX() );
        publisherUpdatePacket.setY( this.location.getBlockY() );
        publisherUpdatePacket.setZ( this.location.getBlockZ() );
        publisherUpdatePacket.setRadius( this.getViewDistance() << 4 );
        this.playerConnection.sendPacket( publisherUpdatePacket );
    }

    public boolean isChunkLoaded( int chunkX, int chunkZ ) {
        return this.loadedChunks.contains( Utils.toLong( chunkX, chunkZ ) );
    }

    public List<Chunk> getLoadedChunk() {
        List<Chunk> chunkList = new ArrayList<>();
        Iterator<Long> iterator = this.loadedChunks.iterator();
        while ( iterator.hasNext() ) {
            long hash = iterator.next();
            int x = Utils.fromHashX( hash );
            int z = Utils.fromHashZ( hash );
            chunkList.add( this.getWorld().getChunk( x, z, this.dimension ) );
            iterator.remove();
        }
        return chunkList;
    }
    // ========== Other ==========

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    public InetSocketAddress getAddress() {
        return (InetSocketAddress) this.playerConnection.getRakNetSession().remoteAddress();
    }

    public int getProtocol() {
        return this.protocol;
    }

    public void setProtocol( int protocol ) {
        this.protocol = protocol;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;

        this.joinServer();

        ChunkRadiusUpdatedPacket chunkRadiusUpdatedPacket = new ChunkRadiusUpdatedPacket();
        chunkRadiusUpdatedPacket.setChunkRadius( viewDistance );
        this.playerConnection.sendPacket( chunkRadiusUpdatedPacket );

        this.needNewChunks();
    }

    public int getInAirTicks() {
        return this.inAirTicks;
    }

    public void addInAirTicks() {
        this.inAirTicks++;
    }

    public void setInAirTicks( int value ) {
        this.inAirTicks = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        if ( this.name == null ) {
            this.name = name;
        }
    }

    public String getXuid() {
        return this.xuid;
    }

    public void setXuid( String xuid ) {
        if ( this.xuid == null ) {
            this.xuid = xuid;
        }
    }

    public String getMinecraftVersion() {
        return this.minecraftVersion;
    }

    public void setMinecraftVersion( String minecraftVersion ) {
        if ( this.minecraftVersion == null ) {
            this.minecraftVersion = minecraftVersion;
        }
    }

    public boolean isBreakingBlock() {
        return this.breakingBlock;
    }

    public void setBreakingBlock( boolean breakingBlock ) {
        this.breakingBlock = breakingBlock;
    }

    public Vector getLasBreakPosition() {
        return this.lasBreakPosition;
    }

    public void setLasBreakPosition( Vector lasBreakPosition ) {
        this.lasBreakPosition = lasBreakPosition;
    }

    public long getLastBreakTime() {
        return this.lastBreakTime;
    }

    public void setLastBreakTime( long lastBreakTime ) {
        this.lastBreakTime = lastBreakTime;
    }

    public long getActionStart() {
        return this.actionStart;
    }

    public boolean hasAction() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION );
    }

    public void setAction( boolean value ) {
        this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION, value ) );
        if ( value ) {
            this.actionStart = this.server.getCurrentTick();
        } else {
            this.actionStart = -1;
        }
    }

    public void resetActionStart() {
        this.actionStart = Server.getInstance().getCurrentTick();
    }

    public void setLocale( Locale locale ) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode( GameMode gameMode ) {
        if ( this.gameMode.equals( gameMode ) ) {
            return;
        }
        this.gameMode = gameMode;

        AdventureSettings adventureSettings = this.adventureSettings;
        adventureSettings.setWorldImmutable( ( gameMode.ordinal() & 0x02 ) > 0 );
        adventureSettings.setBuildAndMine( ( gameMode.ordinal() & 0x02 ) <= 0 );
        adventureSettings.setWorldBuilder( ( gameMode.ordinal() & 0x02 ) <= 0 );
        adventureSettings.setCanFly( ( gameMode.ordinal() & 0x01 ) > 0 );
        adventureSettings.setFlying( gameMode.ordinal() == 0x03 );
        adventureSettings.setNoClip( gameMode.ordinal() == 0x03 );
        adventureSettings.setAttackMobs( gameMode.ordinal() < 0x02 );
        adventureSettings.setAttackPlayers( gameMode.ordinal() < 0x02 );
        adventureSettings.setNoPvP( gameMode.ordinal() == 0x03 );
        adventureSettings.update();

        SetGamemodePacket setGamemodePacket = new SetGamemodePacket();
        setGamemodePacket.setGameMode( gameMode );
        this.playerConnection.sendPacket( setGamemodePacket );
    }

    public AdventureSettings getAdventureSettings() {
        return this.adventureSettings;
    }

    public Set<UUID> getEmotes() {
        return this.emotes;
    }

    // ========== Inventory ==========

    public Inventory getInventory( WindowId windowId ) {
        switch ( windowId ) {
            case PLAYER:
                return this.getInventory();
            case CURSOR_DEPRECATED:
                return this.getCursorInventory();
            case ARMOR_DEPRECATED:
                return this.getArmorInventory();
            default:
                return this.getCurrentInventory();
        }
    }

    public ContainerInventory getCurrentInventory() {
        return this.currentInventory;
    }

    public CursorInventory getCursorInventory() {
        return this.cursorInventory;
    }

    public CraftingTableInventory getCraftingTableInventory() {
        return this.craftingTableInventory;
    }

    public CartographyTableInventory getCartographyTableInventory() {
        return this.cartographyTableInventory;
    }

    public SmithingTableInventory getSmithingTableInventory() {
        return this.smithingTableInventory;
    }

    public AnvilInventory getAnvilInventory() {
        return this.anvilInventory;
    }

    public EnderChestInventory getEnderChestInventory() {
        return this.enderChestInventory;
    }

    public StoneCutterInventory getStoneCutterInventory() {
        return this.stoneCutterInventory;
    }

    public GrindstoneInventory getGrindstoneInventory() {
        return this.grindstoneInventory;
    }

    public void openInventory( Inventory inventory, Vector position, byte windowId ) {
        if ( inventory instanceof ContainerInventory ) {
            ContainerInventory containerInventory = (ContainerInventory) inventory;

            InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent( containerInventory, this );
            Server.getInstance().getPluginManager().callEvent( inventoryOpenEvent );
            if ( inventoryOpenEvent.isCancelled() ) {
                return;
            }

            if ( this.currentInventory != null ) {
                this.closeInventory( this.currentInventory );
            }
            containerInventory.addViewer( this, position, windowId );

            this.currentInventory = containerInventory;
        }
    }

    public void openInventory( Inventory inventory, Vector position ) {
        this.openInventory( inventory, position, (byte) WindowId.OPEN_CONTAINER.getId() );
    }

    public void openInventory( Inventory inventory ) {
        this.openInventory( inventory, this.location );
    }

    public void closeInventory( int windowId, boolean isServerSide ) {
        if ( this.currentInventory != null ) {
            this.currentInventory.removeViewer( this );

            ContainerClosePacket containerClosePacket = new ContainerClosePacket();
            containerClosePacket.setWindowId( windowId );
            containerClosePacket.setServerInitiated( isServerSide );
            this.playerConnection.sendPacket( containerClosePacket );

            Server.getInstance().getPluginManager().callEvent( new InventoryCloseEvent( this.currentInventory, this ) );

            this.currentInventory = null;
        }
    }

    public void closeInventory( Inventory inventory ) {
        if ( inventory instanceof ContainerInventory ) {
            if ( this.currentInventory == inventory ) {
                this.closeInventory( WindowId.OPEN_CONTAINER.getId(), true );
            }
        }
    }

    // ========== Feature Methode ==========

    private void joinServer() {
        if ( !this.spawned ) {
            AvailableCommandsPacket availableCommandsPacket = new AvailableCommandsPacket();
            availableCommandsPacket.setCommands( this.server.getPluginManager().getCommandManager().getCommands() );
            this.playerConnection.sendPacket( availableCommandsPacket );

            this.adventureSettings.setWorldImmutable( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setCanFly( this.gameMode.ordinal() > 0 );
            this.adventureSettings.setNoClip( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setFlying( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setAttackMobs( this.gameMode.ordinal() < 2 );
            this.adventureSettings.setAttackPlayers( this.gameMode.ordinal() < 2 );
            this.adventureSettings.setNoPvP( this.gameMode.ordinal() == 3 );
            this.adventureSettings.update();

            UpdateAttributesPacket updateAttributesPacket = new UpdateAttributesPacket();
            updateAttributesPacket.setEntityId( this.entityId );
            updateAttributesPacket.setAttributes( new ArrayList<>( this.attributes.values() ) );
            updateAttributesPacket.setTick( this.server.getCurrentTick() );
            this.playerConnection.sendPacket( updateAttributesPacket );

            SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
            setEntityDataPacket.setEntityId( this.entityId );
            setEntityDataPacket.setMetadata( this.metadata );
            setEntityDataPacket.setTick( this.server.getCurrentTick() );
            this.playerConnection.sendPacket( setEntityDataPacket );

            this.playerInventory.addViewer( this );
            this.armorInventory.addViewer( this );
            this.cursorInventory.addViewer( this );
            this.craftingTableInventory.addViewer( this );
            this.cartographyTableInventory.addViewer( this );
            this.smithingTableInventory.addViewer( this );
            this.anvilInventory.addViewer( this );

            this.server.addPlayer( this );
            this.server.addToTablist( this );
            if ( this.server.getOnlinePlayers().size() > 1 ) {
                PlayerListPacket playerListPacket = new PlayerListPacket();
                playerListPacket.setType( TablistType.ADD );
                this.server.getPlayerListEntry().forEach( ( uuid, entry ) -> {
                    if ( uuid != this.uuid ) {
                        playerListPacket.getEntries().add( entry );
                    }
                } );
                this.playerConnection.sendPacket( playerListPacket );
            }

            this.getWorld().addEntity( this );
            this.getChunk().addEntity( this );

            if ( this.chunkLoadQueue.isEmpty() ) {
                PlayStatusPacket playStatusPacket = new PlayStatusPacket();
                playStatusPacket.setStatus( PlayStatus.PLAYER_SPAWN );
                this.playerConnection.sendPacket( playStatusPacket );
            }
            PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent( this, "§e" + this.name + " has joined the game" );
            Server.getInstance().getPluginManager().callEvent( playerJoinEvent );
            if ( playerJoinEvent.getJoinMessage() != null && !playerJoinEvent.getJoinMessage().isEmpty() ) {
                Server.getInstance().broadcastMessage( playerJoinEvent.getJoinMessage() );
            }
            for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
                if ( onlinePlayer != null && onlinePlayer.getDimension() == this.getDimension() ) {
                    onlinePlayer.spawn( this );
                    this.spawn( onlinePlayer );
                }
            }
            this.spawned = true;
        }
    }

    public void leaveServer( String reason ) {
        this.getWorld().removeEntity( this );
        this.getChunk().removeEntity( this );

        this.getInventory().removeViewer( this );
        this.getCursorInventory().removeViewer( this );

        this.server.removeFromTablist( this.uuid );
        this.server.removePlayer( this );
        this.despawn();

        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent( this, "§e" + this.name + " left the game" );
        Server.getInstance().getPluginManager().callEvent( playerQuitEvent );
        if ( playerQuitEvent.getQuitMessage() != null && !playerQuitEvent.getQuitMessage().isEmpty() ) {
            this.server.broadcastMessage( playerQuitEvent.getQuitMessage() );
        }
        this.server.getLogger().info( this.name + " logged out reason: " + reason );
    }

    public void disconnect( String disconnectMessage, boolean hideDisconnectScreen ) {
        DisconnectPacket disconnectPacket = new DisconnectPacket();
        disconnectPacket.setDisconnectMessage( disconnectMessage );
        disconnectPacket.setHideDisconnectScreen( hideDisconnectScreen );
        this.playerConnection.sendPacket( disconnectPacket, true );

        this.close();
    }

    public void disconnect( String disconnectMessage ) {
        this.disconnect( disconnectMessage, false );
    }

    public void sendMessage( String message, TextType type ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( type );
        textPacket.setMessage( message );
        textPacket.setLocalized( false );
        textPacket.setXuid( this.xuid );
        textPacket.setDeviceId( this.deviceInfo.getDeviceId() );
        this.playerConnection.sendPacket( textPacket );
    }

    @Override
    public void sendMessage( String message ) {
        this.sendMessage( message, TextType.RAW );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return true;
    }

    @Override
    public boolean hasPermission( String permission, boolean defaultValue ) {
        return true;
    }

    public void sendTip( String message ) {
        this.sendMessage( message, TextType.TIP );
    }

    public void sendPopup( String message ) {
        this.sendMessage( message, TextType.POPUP );
    }

    public void teleport( Location location, PlayerMoveMode mode ) {
        World currentWorld = this.getWorld();
        World world = location.getWorld();

        if ( currentWorld != world ) {
            this.getChunk().removeEntity( this );
            currentWorld.removeEntity( this );

            this.despawn();
            this.resetChunks();

            this.setLocation( new Location( world, world.getSpawnLocation( this.getDimension() ) ) );

            world.addEntity( this );
            this.getChunk().addEntity( this );

            this.spawn();
            this.movePlayer( location, mode );
            return;
        }

        this.setLocation( location );
        this.movePlayer( location, mode );
    }

    public void teleport( Vector vector ) {
        this.teleport( new Location( this.getWorld(), vector ), PlayerMoveMode.RESET );
    }

    public void teleport( Location location ) {
        this.teleport( location, PlayerMoveMode.RESET );
    }

    public void teleport( Player player ) {
        this.teleport( player.getLocation() );
    }

    public void movePlayer( Player player, PlayerMoveMode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( player.getEntityId() );
        playerMovePacket.setX( player.getX() );
        playerMovePacket.setY( player.getY() + player.getEyeHeight() );
        playerMovePacket.setZ( player.getZ() );
        playerMovePacket.setYaw( player.getYaw() );
        playerMovePacket.setPitch( player.getPitch() );
        playerMovePacket.setHeadYaw( player.getYaw() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( player.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( this.server.getCurrentTick() );
        this.playerConnection.sendPacket( playerMovePacket );
    }

    public void movePlayer( Location location, PlayerMoveMode mode ) {
        PlayerMovePacket playerMovePacket = new PlayerMovePacket();
        playerMovePacket.setEntityRuntimeId( this.getEntityId() );
        playerMovePacket.setX( location.getX() );
        playerMovePacket.setY( location.getY() + this.getEyeHeight() );
        playerMovePacket.setZ( location.getZ() );
        playerMovePacket.setYaw( location.getYaw() );
        playerMovePacket.setPitch( location.getPitch() );
        playerMovePacket.setHeadYaw( location.getYaw() );
        playerMovePacket.setMode( mode );
        playerMovePacket.setOnGround( this.isOnGround() );
        playerMovePacket.setRidingEntityId( 0 );
        playerMovePacket.setTick( this.server.getCurrentTick() );
        this.playerConnection.sendPacket( playerMovePacket );
    }

    public void updateAttributes() {
        UpdateAttributesPacket updateAttributesPacket = null;
        for ( Attribute attribute : this.getAttributes() ) {
            if ( attribute.isDirty() ) {
                if ( updateAttributesPacket == null ) {
                    updateAttributesPacket = new UpdateAttributesPacket();
                    updateAttributesPacket.setEntityId( this.getEntityId() );
                }
                updateAttributesPacket.addAttributes( attribute );
            }
        }

        if ( updateAttributesPacket != null ) {
            updateAttributesPacket.setTick( this.server.getCurrentTick() );
            this.playerConnection.sendPacket( updateAttributesPacket );
        }
    }

    public void playSound( Sound sound ) {
        this.playSound( this.location, sound, 1, 1 );
    }

    public void playSound( Sound sound, float volume, float pitch ) {
        this.playSound( this.location, sound, volume, pitch );
    }

    public void playSound( Vector position, Sound sound ) {
        this.playSound( position, sound, 1, 1 );
    }

    public void playSound( Vector position, Sound sound, float volume, float pitch ) {
        PlaySoundPacket playSoundPacket = new PlaySoundPacket();
        playSoundPacket.setPosition( position );
        playSoundPacket.setSound( sound );
        playSoundPacket.setVolume( volume );
        playSoundPacket.setPitch( pitch );
        this.playerConnection.sendPacket( playSoundPacket );
    }
}
