package org.jukeboxmc.player;

import it.unimi.dsi.fastutil.longs.*;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityEventType;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.event.entity.EntityHealEvent;
import org.jukeboxmc.event.inventory.InventoryCloseEvent;
import org.jukeboxmc.event.inventory.InventoryOpenEvent;
import org.jukeboxmc.event.network.PacketSendEvent;
import org.jukeboxmc.event.player.PlayerDeathEvent;
import org.jukeboxmc.event.player.PlayerJoinEvent;
import org.jukeboxmc.event.player.PlayerQuitEvent;
import org.jukeboxmc.event.player.PlayerRespawnEvent;
import org.jukeboxmc.inventory.*;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.enchantment.EnchantmentKnockback;
import org.jukeboxmc.item.enchantment.EnchantmentSharpness;
import org.jukeboxmc.item.enchantment.EnchantmentType;
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
import java.util.concurrent.CompletableFuture;

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
    private EntityFishingHook entityFishingHook;

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
    private int inAirTicks = 0;

    private String name = null;
    private String xuid = null;
    private String minecraftVersion = null;

    private boolean breakingBlock = false;
    private long lastBreakTime = 0;
    private Vector lasBreakPosition;

    private Location respawnLocation = null;
    private Location spawnLocation = null;

    private long actionStart = -1;

    private int viewDistance = 8;
    private long[] chunksInRadius = new long[8 * 8 * 4];

    private final LongPriorityQueue chunkSendQueue;
    private final LongPriorityQueue chunkLoadQueue;

    private final LongSet loadingChunks = new LongOpenHashSet();
    private final LongSet loadedChunks = new LongOpenHashSet();

    private volatile boolean requestedChunks = false;

    private final Set<UUID> emotes = new HashSet<>();
    private final Set<UUID> hiddenPlayers = new HashSet<>();

    private final Map<UUID, List<String>> permissions = new HashMap<>();

    public Player( PlayerConnection playerConnection ) {
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

        ChunkComparator chunkComparator = new ChunkComparator( this );
        this.chunkSendQueue = new LongArrayPriorityQueue( 4096, chunkComparator );
        this.chunkLoadQueue = new LongArrayPriorityQueue( 4096, chunkComparator );

        this.lasBreakPosition = new Vector( 0, 0, 0 );
    }

    public void updateChunks( long currentTick ) {
        if ( this.closed || !this.spawned ) {
            return;
        }

        this.needNewChunks();

        if ( !this.chunkLoadQueue.isEmpty() ) {
            int load = 0;
            while ( !this.chunkLoadQueue.isEmpty() && load <= 4 ) {
                long chunkHash = this.chunkLoadQueue.dequeueLong();
                if ( this.loadingChunks.contains( chunkHash ) || this.loadedChunks.contains( chunkHash ) ) {
                    continue;
                }
                this.requestChunk( Utils.fromHashX( chunkHash ), Utils.fromHashZ( chunkHash ) );
                load++;
            }
            this.chunkLoadQueue.clear();
        }

        if ( !this.chunkSendQueue.isEmpty() ) {
            int sent = 0;
            while ( !this.chunkSendQueue.isEmpty() && sent <= 4 ) {
                long chunkHash = this.chunkSendQueue.dequeueLong();
                this.sendChunk( this.getWorld().getChunk( Utils.fromHashX( chunkHash ), Utils.fromHashZ( chunkHash ), this.dimension ) );
                sent++;
            }
        }
    }

    @Override
    public void update( long currentTick ) {
        if ( this.closed || !this.spawned ) {
            return;
        }

        super.update( currentTick );

        Collection<Entity> nearbyEntities = this.getWorld().getNearbyEntities( this.getBoundingBox().grow( 1, 0.5f, 1 ), this.dimension, null );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                nearbyEntity.onCollideWithPlayer( this );
            }
        }

        if ( !this.onGround && !this.isOnLadder() ) {
            ++this.inAirTicks;
            if ( this.inAirTicks > 5 ) {
                if ( this.location.getY() > this.highestPosition ) {
                    this.highestPosition = this.location.getY();
                }
            }
        } else {
            if ( this.inAirTicks > 0 ) {
                this.inAirTicks = 0;
            }

            this.fallDistance = this.highestPosition - this.location.getY();
            if ( this.fallDistance > 0 ) {
                this.fall();
                this.highestPosition = this.location.getY();
                this.fallDistance = 0;
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
                        this.damage( new EntityDamageEvent( this, 1, EntityDamageEvent.DamageSource.STARVE ) );
                    }
                }
            }
            this.foodTicks++;
            if ( this.foodTicks >= 80 ) {
                this.foodTicks = 0;
            }
        }

        this.updateAttributes();
    }
    // ========== Chunk ==========

    /*
    Ja hab es versucht sync zu machen aber dann ging nix mehr xD

     */

    public void needNewChunks() {
        if ( this.requestedChunks ) return;

        this.requestedChunks = true;

        int currentXChunk = Utils.blockToChunk( this.location.getBlockX() );
        int currentZChunk = Utils.blockToChunk( this.location.getBlockZ() );
        int viewDistance = this.getViewDistance();
        this.server.getScheduler().executeAsync( () -> {
            try {
                int index = 0;
                for ( int sendXChunk = -viewDistance; sendXChunk <= viewDistance; sendXChunk++ ) {
                    for ( int sendZChunk = -viewDistance; sendZChunk <= viewDistance; sendZChunk++ ) {
                        float distance = (float) FastMath.sqrt( sendZChunk * sendZChunk + sendXChunk * sendXChunk );
                        int chunkDistance = FastMath.round( distance );

                        if ( chunkDistance <= viewDistance ) {
                            long hash = Utils.toLong( currentXChunk + sendXChunk, currentZChunk + sendZChunk );

                            if ( !this.loadedChunks.contains( hash ) && !this.loadingChunks.contains( hash ) ) {
                                this.chunksInRadius[index++] = hash;
                            }
                        } else {
                            //Chunk unload queue
                            //Checkn ob spieler den chunk in sichtweite haben wenn ja unloaden
                        }
                    }
                }
                final int chunkCount = index;
                this.server.getScheduler().execute( () -> {
                    if ( chunkCount > 0 ) {
                        for ( int i = 0; i < chunkCount; i++ ) {
                            this.chunkLoadQueue.enqueue( this.chunksInRadius[i] );
                        }
                    }

                    LongIterator iterator = this.loadedChunks.iterator();
                    while ( iterator.hasNext() ) {
                        long hash = iterator.nextLong();
                        int x = Utils.fromHashX( hash );
                        int z = Utils.fromHashZ( hash );

                        if ( FastMath.abs( x - currentXChunk ) > viewDistance || FastMath.abs( z - currentZChunk ) > viewDistance ) {
                            iterator.remove();
                        }
                    }

                    LongIterator loadingIterator = this.loadingChunks.iterator();
                    while ( loadingIterator.hasNext() ) {
                        long hash = loadingIterator.nextLong();
                        int x = Utils.fromHashX( hash );
                        int z = Utils.fromHashZ( hash );

                        if ( FastMath.abs( x - currentXChunk ) > viewDistance || FastMath.abs( z - currentZChunk ) > viewDistance ) {
                            loadingIterator.remove();
                        }
                    }
                } );
            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                this.requestedChunks = false;
            }
        });
    }

    public CompletableFuture<Chunk> requestChunk( int chunkX, int chunkZ ) {
        try {
            long chunkHash = Utils.toLong( chunkX, chunkZ );

            this.loadingChunks.add( chunkHash );
            return this.getWorld().requestChunk( chunkX, chunkZ, this.dimension ).whenCompleteAsync( ( chunk, throwable ) -> {
                this.chunkSendQueue.enqueue( chunkHash );
            }, server.getScheduler() );
        } catch ( Throwable throwable ) {
            throwable.printStackTrace();
            return null;
        }
    }

    public void resetChunks() {
        this.loadedChunks.clear();
        this.loadingChunks.clear();
    }

    public void sendChunk( Chunk chunk ) {
        this.server.getScheduler().executeAsync( () -> {
            this.sendNetworkChunkPublisher();
            //maybe weil ich es direct sende? nein nein
            this.playerConnection.sendPacket( chunk.createLevelChunkPacket(), true );

            this.server.getScheduler().execute( () -> {
                long chunkHash = chunk.toChunkHash();
                this.loadingChunks.remove( chunkHash );
                this.loadedChunks.add( chunkHash );
            });
        });
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
        this.chunksInRadius = new long[viewDistance * viewDistance * 4];

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

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation( Location respawnLocation ) {
        this.respawnLocation = respawnLocation;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
        setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.SpawnType.PLAYER );
        setSpawnPositionPacket.setPlayerPosition( this.spawnLocation );
        setSpawnPositionPacket.setDimension( this.dimension );
        setSpawnPositionPacket.setWorldSpawn( this.getWorld().getSpawnLocation( this.dimension ) );
        this.playerConnection.sendPacket( setSpawnPositionPacket );
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

    public EntityFishingHook getEntityFishingHook() {
        return this.entityFishingHook;
    }

    public void setEntityFishingHook( EntityFishingHook entityFishingHook ) {
        this.entityFishingHook = entityFishingHook;
    }

    public Set<UUID> getEmotes() {
        return this.emotes;
    }

    // ========== Inventory ==========

    public Inventory getInventory( WindowId windowId ) {
        return switch ( windowId ) {
            case PLAYER -> this.getInventory();
            case CURSOR_DEPRECATED -> this.getCursorInventory();
            case ARMOR_DEPRECATED -> this.getArmorInventory();
            default -> this.getCurrentInventory();
        };
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

    public List<Item> getDrops() {
        List<Item> drops = new ArrayList<>();
        for ( Item content : this.playerInventory.getContents() ) {
            if ( content != null && !( content instanceof ItemAir ) ) {
                drops.add( content );
            }
        }
        for ( Item content : this.cursorInventory.getContents() ) {
            if ( content != null && !( content instanceof ItemAir ) ) {
                drops.add( content );
            }
        }
        for ( Item content : this.armorInventory.getContents() ) {
            if ( content != null && !( content instanceof ItemAir ) ) {
                drops.add( content );
            }
        }
        return drops;
    }

    // ========== Feature Methode ==========

    @Override
    public void sendMessage( String message ) {
        this.sendMessage( message, TextType.RAW );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return this.permissions.containsKey( this.uuid ) && this.permissions.get( this.uuid ).contains( permission.toLowerCase() ) || this.isOp() || permission.isEmpty();
    }

    public void addPermission( String permission ) {
        if ( !this.permissions.containsKey( this.uuid ) ) {
            this.permissions.put( this.uuid, new ArrayList<>() );
        }
        this.permissions.get( this.uuid ).add( permission.toLowerCase() );
    }

    public void removePermission( String permission ) {
        if ( this.permissions.containsKey( this.uuid ) ) {
            this.permissions.get( this.uuid ).remove( permission );
        }
    }

    public boolean isOp() {
        return this.adventureSettings.isOperator();
    }


    public void setOp( boolean value ) {
        this.sendCommandData();
        this.adventureSettings.setOperator( value );
        this.adventureSettings.update();
        this.sendCommandData();
    }

    @Override
    public boolean damage( EntityDamageEvent event ) {
        if ( this.adventureSettings.isCanFly() && event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ) {
            return false;
        }
        return !this.gameMode.equals( GameMode.CREATIVE ) && !this.gameMode.equals( GameMode.SPECTATOR ) && super.damage( event );
    }

    @Override
    protected float applyArmorReduction( EntityDamageEvent event, boolean damageArmor ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ||
                event.getDamageSource().equals( EntityDamageEvent.DamageSource.VOID ) ||
                event.getDamageSource().equals( EntityDamageEvent.DamageSource.DROWNING ) ) {
            return event.getDamage();
        }

        float damage = event.getDamage();
        float maxReductionDiff = 25.0f - this.armorInventory.getTotalArmorValue() * 0.04f;
        float amplifiedDamage = damage * maxReductionDiff;
        if ( damageArmor ) {
            this.armorInventory.damageEvenly( damage );
        }

        return amplifiedDamage / 25.0F;
    }

    @Override
    protected void kill() {
        if ( !this.isDead ) {
            super.kill();
            EntityEventPacket entityEventPacket = new EntityEventPacket();
            entityEventPacket.setEntityId( this.entityId );
            entityEventPacket.setEntityEventType( EntityEventType.DEATH );
            this.playerConnection.sendPacket( entityEventPacket );

            String deathMessage = switch ( this.lastDamageSource ) {
                case ENTITY_ATTACK -> this.getNameTag() + " was slain by " + this.getLastDamageEntity().getNameTag();
                case FALL -> this.getNameTag() + " fell from a high place";
                case LAVA -> this.getNameTag() + " tried to swim in lava";
                case FIRE -> this.getNameTag() + " went up in flames";
                case VOID -> this.getNameTag() + " fell out of the world";
                case CACTUS -> this.getNameTag() + " was pricked to death";
                case STARVE -> this.getNameTag() + " starved to death";
                case ON_FIRE -> this.getNameTag() + " burned to death";
                case DROWNING -> this.getNameTag() + " drowned";
                case HARM_EFFECT -> this.getNameTag() + " was killed by magic";
                case ENTITY_EXPLODE -> this.getNameTag() + " blew up";
                case PROJECTILE -> this.getNameTag() + " has been shot";
                case API -> this.getNameTag() + " was killed by setting health to 0";
                case COMMAND -> this.getNameTag() + " died";
            };

            PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent( this, deathMessage, true, this.getDrops() );
            this.server.getPluginManager().callEvent( playerDeathEvent );

            if ( playerDeathEvent.isDropInventory() ) {
                for ( Item drop : playerDeathEvent.getDrops() ) {
                    this.getWorld().dropItem( drop, this.location, null ).spawn();
                }

                this.playerInventory.clear();
                this.cursorInventory.clear();
                this.armorInventory.clear();
            }

            if ( playerDeathEvent.getDeathMessage() != null && !playerDeathEvent.getDeathMessage().isEmpty() ) {
                this.server.broadcastMessage( playerDeathEvent.getDeathMessage() );
            }

            this.respawnLocation = this.getWorld().getSpawnLocation( this.dimension ).add( 0, this.getEyeHeight(), 0 );

            RespawnPositionPacket respawnPositionPacket = new RespawnPositionPacket();
            respawnPositionPacket.setEntityId( this.entityId );
            respawnPositionPacket.setRespawnState( RespawnPositionPacket.RespawnState.SEARCHING_FOR_SPAWN );
            respawnPositionPacket.setPosition( this.respawnLocation );
            this.playerConnection.sendPacket( respawnPositionPacket );
        }
    }

    public boolean attackWithItemInHand( Entity target ) {
        if ( target instanceof Player ) {
            Player player = (Player) target;

            boolean success = false;

            EntityDamageEvent.DamageSource damageSource = EntityDamageEvent.DamageSource.ENTITY_ATTACK;
            float damage = this.getAttackDamage();

            EnchantmentSharpness sharpness = (EnchantmentSharpness) this.playerInventory.getItemInHand().getEnchantment( EnchantmentType.SHARPNESS );
            if ( sharpness != null ) {
                damage += sharpness.getLevel() * 1.25f;
            }

            int knockbackLevel = 0;

            EnchantmentKnockback knockback = (EnchantmentKnockback) this.playerInventory.getItemInHand().getEnchantment( EnchantmentType.KNOCKBACK );
            if ( knockback != null ) {
                knockbackLevel += knockback.getLevel();
            }

            if ( damage > 0 ) {
                boolean crit = this.fallDistance > 0 && !this.onGround && !this.isOnLadder() && !this.isInWater();
                if ( crit && damage > 0.0f ) {
                    damage *= 1.5;
                }
                if ( ( success = player.damage( new EntityDamageByEntityEvent( player, this, damage, damageSource ) ) ) ) {
                    if ( knockbackLevel > 0 ) {
                        Vector targetVelocity = target.getVelocity();
                        player.setVelocity( targetVelocity.add(
                                (float) ( -Math.sin( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.3 ),
                                0.1f,
                                (float) ( Math.cos( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.3 ) ) );

                        Vector ownVelocity = this.getVelocity();
                        ownVelocity.setX( ownVelocity.getX() * 0.6F );
                        ownVelocity.setZ( ownVelocity.getZ() * 0.6F );
                        this.setVelocity( ownVelocity );

                        this.setSprinting( false );
                    }
                }
            }
            this.exhaust( 0.3f );
            return success;
        }
        return false;
    }

    @Override
    public boolean canCollideWith( Entity entity ) {
        return false;
    }

    public void respawn() {
        if ( this.isDead ) {
            PlayerRespawnEvent playerRespawnEvent = new PlayerRespawnEvent( this, this.respawnLocation );
            this.server.getPluginManager().callEvent( playerRespawnEvent );

            this.lastDamageEntity = null;
            this.lastDamageSource = null;
            this.lastDamage = 0;

            this.updateMetadata();
            for ( Attribute attribute : this.attributes.values() ) {
                attribute.reset();
            }
            this.updateAttributes();

            this.teleport( playerRespawnEvent.getRespawnLocation() );
            this.respawnLocation = null;

            this.spawn();

            this.setBurning( false );
            this.setVelocity( Vector.zero() );

            this.playerInventory.sendContents( this );
            this.cursorInventory.sendContents( this );
            this.armorInventory.sendArmorContent( this );

            EntityEventPacket entityEventPacket = new EntityEventPacket();
            entityEventPacket.setEntityId( this.entityId );
            entityEventPacket.setEntityEventType( EntityEventType.RESPAWN );
            this.getWorld().sendDimensionPacket( entityEventPacket, this.dimension );

            this.playerInventory.getItemInHand().addToHand( this );

            this.isDead = false;
        }
    }

    private void joinServer() {
        if ( !this.spawned ) {
            this.server.addPlayer( this );

            if ( this.server.isOperatorInFile( this.name ) ) {
                this.adventureSettings.setOperator( true );
            }

            this.adventureSettings.setWorldImmutable( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setCanFly( this.gameMode.ordinal() > 0 );
            this.adventureSettings.setNoClip( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setFlying( this.gameMode.ordinal() == 3 );
            this.adventureSettings.setAttackMobs( this.gameMode.ordinal() < 2 );
            this.adventureSettings.setAttackPlayers( this.gameMode.ordinal() < 2 );
            this.adventureSettings.setNoPvP( this.gameMode.ordinal() == 3 );
            this.adventureSettings.update();

            this.sendCommandData();

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

            this.getWorld().setSpawnLocation( this.getWorld().getSpawnLocation( this.dimension ) );

            if ( this.spawnLocation == null ) {
                Location spawnLocation = this.getWorld().getSpawnLocation( this.dimension );
                this.setSpawnLocation( spawnLocation );
            }

            this.playerInventory.addViewer( this );
            this.armorInventory.addViewer( this );
            this.cursorInventory.addViewer( this );
            this.craftingTableInventory.addViewer( this );
            this.cartographyTableInventory.addViewer( this );
            this.smithingTableInventory.addViewer( this );
            this.anvilInventory.addViewer( this );

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

            World world = this.getWorld();
            Chunk chunk;
            if ( !world.isChunkLoaded( this.getChunkX(), this.getChunkZ(), this.getDimension() ) ) {
                chunk = world.loadChunk( this.getChunkX(), this.getChunkZ(), this.getDimension() );
            } else {
                chunk = world.getChunk( this.getChunkX(), this.getChunkZ(), this.getDimension() );
            }
            this.sendChunk( chunk );
            world.addEntity( this );

            PlayStatusPacket playStatusPacket = new PlayStatusPacket();
            playStatusPacket.setStatus( PlayStatus.PLAYER_SPAWN );
            this.playerConnection.sendPacket( playStatusPacket );

            PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent( this, "§e" + this.name + " has joined the game" );
            Server.getInstance().getPluginManager().callEvent( playerJoinEvent );
            if ( playerJoinEvent.getJoinMessage() != null && !playerJoinEvent.getJoinMessage().isEmpty() ) {
                Server.getInstance().broadcastMessage( playerJoinEvent.getJoinMessage() );
            }
            this.server.getLogger().info( this.name + " logged in [World=" + this.getWorld().getName() + ", X=" + this.getBlockX() + ", Y=" + this.getBlockY() + ", Z=" + this.getBlockZ() + ", Dimension=" + this.getDimension().name() + "]" );

            for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
                if ( onlinePlayer != null && onlinePlayer.getDimension() == this.getDimension() ) {
                    onlinePlayer.spawn( this );
                    this.spawn( onlinePlayer );
                }
            }

            for ( Player onlinePlayer : this.server.getOnlinePlayers() ) {
                onlinePlayer.getArmorInventory().sendContents( this );
                this.getArmorInventory().sendArmorContent( onlinePlayer );
            }
            this.highestPosition = this.location.getY();
            this.spawned = true;
        }
    }

    public void sendPacket( Packet packet, boolean direct ) {
        PacketSendEvent packetSendEvent = new PacketSendEvent( this, packet );
        this.server.getPluginManager().callEvent( packetSendEvent );
        if ( packetSendEvent.isCancelled() ) {
            return;
        }
        this.playerConnection.sendPacket( packetSendEvent.getPacket(), direct );
    }

    public void sendPacket( Packet packet ) {
        this.sendPacket( packet, false );
    }

    public void leaveServer( String reason ) {
        if ( this.spawned ) {
            this.getWorld().removeEntity( this );
            this.getChunk().removeEntity( this );

            this.getInventory().removeViewer( this );
            this.getArmorInventory().removeViewer( this );
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
    }

    @Override
    public void close() {
        this.leaveServer( "Disconnect" );
    }

    public void sendCommandData() {
        AvailableCommandsPacket availableCommandsPacket = new AvailableCommandsPacket();
        List<Command> commandList = new ArrayList<>();
        for ( Command command : this.server.getPluginManager().getCommandManager().getCommands() ) {
            if ( !this.hasPermission( command.getPermission() ) ) {
                continue;
            }
            commandList.add( command );
        }
        availableCommandsPacket.setCommands( commandList );
        this.playerConnection.sendPacket( availableCommandsPacket );
    }

    public void disconnect( String disconnectMessage, boolean hideDisconnectScreen ) {
        DisconnectPacket disconnectPacket = new DisconnectPacket();
        disconnectPacket.setDisconnectMessage( disconnectMessage );
        disconnectPacket.setHideDisconnectScreen( hideDisconnectScreen );
        this.sendPacket( disconnectPacket, true );
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
            this.despawn();
            currentWorld.getPlayers().forEach( player -> player.despawn( this ) );

            this.getChunk().removeEntity( this );
            currentWorld.removeEntity( this );

            this.resetChunks();

            this.setLocation( location );
            this.needNewChunks();

            location.getWorld().loadChunk( location.getChunkX(), location.getChunkZ(), this.dimension );

            world.addEntity( this );
            this.getChunk().addEntity( this );
            this.spawn();
            world.getPlayers().forEach( player -> player.spawn( this ) );
            this.movePlayer( location, mode );
            return;
        }

        this.fallDistance = 0;
        this.setLocation( location );
        this.movePlayer( location, mode );
    }

    public void teleport( Vector vector ) {
        this.teleport( new Location( this.getWorld(), vector ), PlayerMoveMode.TELEPORT );
    }

    public void teleport( Location location ) {
        this.teleport( location, PlayerMoveMode.TELEPORT );
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

    public boolean canSeePlayer( Player player ) {
        return !this.hiddenPlayers.contains( player.getUUID() );
    }

    public void hidePlayer( Player player ) {
        if ( this != player ) {
            player.despawn( this );
            this.hiddenPlayers.add( player.getUUID() );
        }
    }

    public void showPlayer( Player player ) {
        if ( this != player ) {
            player.spawn( this );
            this.hiddenPlayers.remove( player.getUUID() );
        }
    }

    @Override
    public boolean equals( Object obj ) {
        if ( !( obj instanceof Player ) ) {
            return false;
        }
        Player other = (Player) obj;
        return Objects.equals( this.uuid, other.uuid ) && this.entityId == other.entityId;
    }
}
