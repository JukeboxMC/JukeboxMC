package org.jukeboxmc.player;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.packet.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityMoveable;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.passiv.EntityHuman;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.event.entity.EntityHealEvent;
import org.jukeboxmc.event.inventory.InventoryCloseEvent;
import org.jukeboxmc.event.inventory.InventoryOpenEvent;
import org.jukeboxmc.event.player.*;
import org.jukeboxmc.form.Form;
import org.jukeboxmc.form.FormListener;
import org.jukeboxmc.form.NpcDialogueForm;
import org.jukeboxmc.inventory.*;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemArmor;
import org.jukeboxmc.item.enchantment.Enchantment;
import org.jukeboxmc.item.enchantment.EnchantmentKnockback;
import org.jukeboxmc.item.enchantment.EnchantmentSharpness;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.data.LoginData;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.potion.EffectType;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.ChunkLoader;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player extends EntityHuman implements ChunkLoader, CommandSender, InventoryHolder {

    private final Server server;
    private final PlayerConnection playerConnection;
    private final AdventureSettings adventureSettings;
    private String name;
    private GameMode gameMode;

    private int closingWindowId = Integer.MIN_VALUE;

    private ContainerInventory currentInventory;
    private CraftingGridInventory craftingGridInventory;
    private final CreativeItemCacheInventory creativeItemCacheInventory;
    private final CursorInventory cursorInventory;
    private final CraftingTableInventory craftingTableInventory;
    private final CartographyTableInventory cartographyTableInventory;
    private final SmithingTableInventory smithingTableInventory;
    private final AnvilInventory anvilInventory;
    private final EnderChestInventory enderChestInventory;
    private final StoneCutterInventory stoneCutterInventory;
    private final GrindstoneInventory grindstoneInventory;

    private int inAirTicks = 0;
    private float highestPosition = 0;

    private long lastBreakTime = 0;
    private Vector lasBreakPosition;
    private boolean breakingBlock = false;

    private Location spawnLocation;
    private Location respawnLocation = null;

    private final Map<UUID, Set<String>> permissions = new HashMap<>();

    private EntityFishingHook entityFishingHook;

    private int formId;
    private int serverSettingsForm = -1;
    private final Int2ObjectMap<Form<?>> forms = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectMap<FormListener<?>> formListeners = new Int2ObjectOpenHashMap<>();

    private final ObjectArrayList<NpcDialogueForm> npcDialogueForms = new ObjectArrayList<>();

    public Player( Server server, PlayerConnection playerConnection ) {
        this.server = server;
        this.playerConnection = playerConnection;
        this.gameMode = playerConnection.getServer().getGameMode();
        this.adventureSettings = new AdventureSettings( this );

        this.creativeItemCacheInventory = new CreativeItemCacheInventory( this );
        this.craftingGridInventory = new SmallCraftingGridInventory( this );
        this.cursorInventory = new CursorInventory( this );
        this.craftingTableInventory = new CraftingTableInventory( this );
        this.cartographyTableInventory = new CartographyTableInventory( this );
        this.smithingTableInventory = new SmithingTableInventory( this );
        this.anvilInventory = new AnvilInventory( this );
        this.enderChestInventory = new EnderChestInventory( this );
        this.stoneCutterInventory = new StoneCutterInventory( this );
        this.grindstoneInventory = new GrindstoneInventory( this );
        this.lasBreakPosition = new Vector( 0, 0, 0 );

        this.spawnLocation = this.location.getWorld().getSpawnLocation();
    }

    @Override
    public void update( long currentTick ) {
        if ( !this.playerConnection.isLoggedIn() ) {
            return;
        }
        super.update( currentTick );

        Collection<Entity> nearbyEntities = this.getWorld().getNearbyEntities( this.getBoundingBox().grow( 1, 0.5f, 1 ), this.location.getDimension(), null );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                if ( nearbyEntity instanceof EntityMoveable entityMoveable ) {
                    entityMoveable.onCollideWithPlayer( this );
                }
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
                        if ( this.getGameMode().equals( GameMode.SURVIVAL ) ) {
                            this.exhaust( 3 );
                        }
                    }
                } else if ( hunger <= 0 ) {
                    if ( health == -1 ) {
                        health = this.getHealth();
                    }

                    if ( ( difficulty.equals( Difficulty.NORMAL ) && health > 2 ) || ( difficulty.equals( Difficulty.HARD ) && health > 1 ) ) {
                        this.damage( new EntityDamageEvent( this, 1, EntityDamageEvent.DamageSource.STARVE ) );
                    }
                }
            }
            this.foodTicks++;
            if ( this.foodTicks >= 80 ) {
                this.foodTicks = 0;
            }

            if ( this.hasEffect( EffectType.HUNGER ) ) {
                this.exhaust( 0.1f * ( this.getEffect( EffectType.HUNGER ).getAmplifier() + 1 ) );
            }
        }

        boolean breathing = !this.isInWater() || this.hasEffect( EffectType.WATER_BREATHING );
        if ( this.metadata.getFlag( EntityFlag.BREATHING ) != breathing && this.gameMode.equals( GameMode.SURVIVAL ) ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.BREATHING, breathing ) );
        }

        short air = this.metadata.getShort( EntityData.AIR_SUPPLY );
        short maxAir = this.metadata.getShort( EntityData.MAX_AIR_SUPPLY );

        if ( this.gameMode.equals( GameMode.SURVIVAL ) ) {
            if ( !breathing ) {
                if ( --air < 0 ) {
                    if ( currentTick % 20 == 0 ) {
                        this.damage( new EntityDamageEvent( this, 2f, EntityDamageEvent.DamageSource.DROWNING ) );
                    }
                } else {
                    this.updateMetadata( this.metadata.setShort( EntityData.AIR_SUPPLY, air ) );
                }
            } else {
                if ( air != maxAir ) {
                    this.updateMetadata( this.metadata.setShort( EntityData.AIR_SUPPLY, maxAir ) );
                }
            }
        }

        this.updateAttributes();
    }

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }

    public AdventureSettings getAdventureSettings() {
        return this.adventureSettings;
    }

    public boolean isSpawned() {
        return this.playerConnection.isSpawned();
    }

    public boolean isLoggedIn() {
        return this.playerConnection.isLoggedIn();
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        if ( !this.playerConnection.isLoggedIn() ) {
            this.name = name;
        }
    }

    public String getXuid() {
        return this.playerConnection.getLoginData().getXuid();
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode( GameMode gameMode ) {
        this.gameMode = gameMode;

        this.adventureSettings.set( AdventureSettings.Type.WORLD_IMMUTABLE, this.gameMode.ordinal() == 3 );
        this.adventureSettings.set( AdventureSettings.Type.ALLOW_FLIGHT, this.gameMode.ordinal() > 0 );
        this.adventureSettings.set( AdventureSettings.Type.NO_CLIP, this.gameMode.ordinal() == 3 );
        this.adventureSettings.set( AdventureSettings.Type.FLYING, this.gameMode.ordinal() == 3 );
        this.adventureSettings.set( AdventureSettings.Type.ATTACK_MOBS, this.gameMode.ordinal() < 2 );
        this.adventureSettings.set( AdventureSettings.Type.ATTACK_PLAYERS, this.gameMode.ordinal() < 2 );
        this.adventureSettings.set( AdventureSettings.Type.NO_PVM, this.gameMode.ordinal() == 3 );
        this.adventureSettings.update();

        SetPlayerGameTypePacket setPlayerGameTypePacket = new SetPlayerGameTypePacket();
        setPlayerGameTypePacket.setGamemode( gameMode.getId() );
        this.playerConnection.sendPacket( setPlayerGameTypePacket );
    }

    @Override
    public void sendMessage( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.RAW );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.playerConnection.sendPacket( textPacket );
    }

    public void sendTip( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.TIP );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.playerConnection.getLoginData().getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.playerConnection.sendPacket( textPacket );
    }

    public void sendPopup( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.POPUP );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.playerConnection.getLoginData().getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.playerConnection.sendPacket( textPacket );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return this.permissions.containsKey( this.uuid ) && this.permissions.get( this.uuid ).contains( permission.toLowerCase() ) || this.isOp() || permission.isEmpty();
    }

    public void addPermission( String permission ) {
        if ( !this.permissions.containsKey( this.uuid ) ) {
            this.permissions.put( this.uuid, new HashSet<>() );
        }
        this.permissions.get( this.uuid ).add( permission.toLowerCase() );
        this.sendCommandData();
    }

    public void addPermissions( Collection<String> permissions ) {
        if ( !this.permissions.containsKey( this.uuid ) ) {
            this.permissions.put( this.uuid, new HashSet<>( permissions ) );
        } else {
            this.permissions.get( this.uuid ).addAll( permissions );
        }
        this.sendCommandData();
    }

    public void removePermission( String permission ) {
        if ( this.permissions.containsKey( this.uuid ) ) {
            this.permissions.get( this.uuid ).remove( permission );
        }
        this.sendCommandData();
    }

    public void removePermissions( Collection<String> permissions ) {
        if ( this.permissions.containsKey( this.uuid ) ) {
            this.permissions.get( this.uuid ).removeAll( permissions );
        }
        this.sendCommandData();
    }

    public boolean isOp() {
        return this.adventureSettings.get( AdventureSettings.Type.OPERATOR );
    }

    public void setOp( boolean value ) {
        this.adventureSettings.set( AdventureSettings.Type.OPERATOR, value );
        this.adventureSettings.update();
        if ( value ) {
            this.server.addOperatorToFile( this.getName() );
        } else {
            this.server.removeOperatorFromFile( this.getName() );
        }
        this.sendCommandData();
    }

    public int getChunkRadius() {
        return this.playerConnection.getPlayerChunkManager().getChunkRadius();
    }

    public void setChunkRadius( int chunkRadius ) {
        this.playerConnection.getPlayerChunkManager().setChunkRadius( chunkRadius );
    }

    public boolean isChunkLoaded( int chunkX, int chunkZ ) {
        return this.playerConnection.getPlayerChunkManager().isChunkInView( chunkX, chunkZ );
    }

    public void resendChunk( int chunkX, int chunkZ ) {
        this.playerConnection.getPlayerChunkManager().resendChunk( chunkX, chunkZ );
    }

    public CraftingGridInventory getCraftingGridInventory() {
        return this.craftingGridInventory;
    }

    public void setCraftingGridInventory( CraftingGridInventory craftingGridInventory ) {
        this.craftingGridInventory = craftingGridInventory;
    }

    public CreativeItemCacheInventory getCreativeItemCacheInventory() {
        return creativeItemCacheInventory;
    }

    public int getClosingWindowId() {
        return this.closingWindowId;
    }

    public void setClosingWindowId( int closingWindowId ) {
        this.closingWindowId = closingWindowId;
    }

    public ContainerInventory getCurrentInventory() {
        return this.currentInventory;
    }

    public void setCurrentInventory( ContainerInventory currentInventory ) {
        this.currentInventory = currentInventory;
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

    public void openInventory( ContainerInventory inventory, Vector position, byte windowId ) {
        if ( this.currentInventory != null ) {
            return;
        }
        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent( inventory, this );
        Server.getInstance().getPluginManager().callEvent( inventoryOpenEvent );
        if ( inventoryOpenEvent.isCancelled() ) {
            return;
        }

        inventory.addViewer( this, position, windowId );

        this.currentInventory = inventory;
    }

    public void openInventory( ContainerInventory inventory, Vector position ) {
        this.openInventory( inventory, position, (byte) WindowId.OPEN_CONTAINER.getId() );
    }

    public void openInventory( ContainerInventory inventory ) {
        this.openInventory( inventory, this.location );
    }

    public void closeInventory( ContainerInventory inventory ) {
        if ( this.currentInventory == inventory ) {
            this.closeInventory( (byte) WindowId.OPEN_CONTAINER.getId() );
        }
    }

    public void closeInventory( byte windowId ) {
        if ( this.currentInventory != null ) {

            ContainerClosePacket containerClosePacket = new ContainerClosePacket();
            containerClosePacket.setId( windowId );
            containerClosePacket.setUnknownBool0( this.closingWindowId != windowId );
            this.playerConnection.sendPacket( containerClosePacket );

            this.currentInventory.removeViewer( this );

            Server.getInstance().getPluginManager().callEvent( new InventoryCloseEvent( this.currentInventory, this ) );

            this.currentInventory = null;
        }
    }

    public void closeInventory( byte windowId, boolean isServerSide ) {
        if ( this.currentInventory != null ) {

            ContainerClosePacket containerClosePacket = new ContainerClosePacket();
            containerClosePacket.setId( windowId );
            containerClosePacket.setUnknownBool0( isServerSide );
            this.playerConnection.sendPacket( containerClosePacket );

            this.currentInventory.removeViewer( this );

            Server.getInstance().getPluginManager().callEvent( new InventoryCloseEvent( this.currentInventory, this ) );

            this.currentInventory = null;
        } else {
            ContainerClosePacket containerClosePacket = new ContainerClosePacket();
            containerClosePacket.setId( windowId );
            containerClosePacket.setUnknownBool0( isServerSide );
            this.playerConnection.sendPacket( containerClosePacket );
        }
    }

    public void teleport( Player player ) {
        this.teleport( player.getLocation() );
    }

    public void teleport( Location location ) {
        World currentWorld = this.getWorld();
        World world = location.getWorld();
        Dimension fromDimension = this.location.getDimension();

        //TODO DESPAWN PLAYER AND SPAWN TO NEW DIMENSION
        if ( !fromDimension.equals( location.getDimension() ) ) {
            this.playerConnection.getPlayerChunkManager().clear();

            SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
            setSpawnPositionPacket.setDimensionId( location.getDimension().ordinal() );
            setSpawnPositionPacket.setSpawnPosition( location.toVector3i().div( 8, 8, 8 ) );
            setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.Type.PLAYER_SPAWN );
            this.playerConnection.sendPacket( setSpawnPositionPacket );

            ChangeDimensionPacket changeDimensionPacket = new ChangeDimensionPacket();
            changeDimensionPacket.setPosition( this.location.toVector3f() );
            changeDimensionPacket.setDimension( location.getDimension().ordinal() );
            changeDimensionPacket.setRespawn( false );
            this.playerConnection.sendPacket( changeDimensionPacket );
        } else if ( !currentWorld.getName().equals( world.getName() ) ) {
            this.despawn();

            currentWorld.getPlayers().forEach( player -> player.despawn( this ) );

            this.getChunk().removeEntity( this );
            currentWorld.removeEntity( this );

            this.playerConnection.getPlayerChunkManager().clear();

            this.setLocation( location );
            this.playerConnection.getPlayerChunkManager().queueNewChunks();

            world.addEntity( this );
            this.getChunk().addEntity( this );
            this.spawn();

            world.getPlayers().forEach( player -> player.spawn( this ) );
        }
        this.location = location;
        this.move( location, MovePlayerPacket.Mode.TELEPORT );
    }

    private void move( Location location, MovePlayerPacket.Mode mode ) {
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.setRuntimeEntityId( this.entityId );
        movePlayerPacket.setPosition( location.toVector3f().add( 0, this.getEyeHeight(), 0 ) );
        movePlayerPacket.setRotation( Vector3f.from( location.getPitch(), location.getYaw(), location.getYaw() ) );
        movePlayerPacket.setMode( mode );
        if ( mode == MovePlayerPacket.Mode.TELEPORT ) {
            movePlayerPacket.setTeleportationCause( MovePlayerPacket.TeleportationCause.BEHAVIOR );
        }
        movePlayerPacket.setOnGround( this.onGround );
        movePlayerPacket.setRidingRuntimeEntityId( 0 );
        movePlayerPacket.setTick( this.server.getCurrentTick() );
        this.playerConnection.sendPacket( movePlayerPacket );
    }

    public void kick( String reason, boolean hideScreen ) {
        PlayerKickEvent playerKickEvent = new PlayerKickEvent( this, reason );
        Server.getInstance().getPluginManager().callEvent( playerKickEvent );
        if ( playerKickEvent.isCancelled() ) {
            return;
        }
        this.close();
        this.playerConnection.disconnect( playerKickEvent.getReason(), hideScreen );
    }

    public void kick( String reason ) {
        this.kick( reason, false );
    }

    public void sendToast( String title, String content ) {
        ToastRequestPacket toastRequestPacket = new ToastRequestPacket();
        toastRequestPacket.setTitle( title );
        toastRequestPacket.setContent( content );
        this.playerConnection.sendPacket( toastRequestPacket );
    }

    public void sendCommandData() {
        AvailableCommandsPacket availableCommandsPacket = new AvailableCommandsPacket();
        Set<CommandData> commandList = new HashSet<>();
        for ( Command command : this.server.getPluginManager().getCommandManager().getCommands() ) {
            if ( !this.hasPermission( command.getCommandData().getPermission() ) ) {
                continue;
            }
            commandList.add( command.getCommandData().toNetwork() );
        }
        availableCommandsPacket.getCommands().addAll( commandList );
        this.getPlayerConnection().sendPacket( availableCommandsPacket );
    }

    public boolean isBreakingBlock() {
        return this.breakingBlock;
    }

    public void setBreakingBlock( boolean breakingBlock ) {
        this.breakingBlock = breakingBlock;
    }

    public long getLastBreakTime() {
        return this.lastBreakTime;
    }

    public void setLastBreakTime( long lastBreakTime ) {
        this.lastBreakTime = lastBreakTime;
    }

    public Vector getLasBreakPosition() {
        return this.lasBreakPosition;
    }

    public void setLasBreakPosition( Vector lasBreakPosition ) {
        this.lasBreakPosition = lasBreakPosition;
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
        playSoundPacket.setPosition( position.toVector3f() );
        playSoundPacket.setSound( sound.getSound() );
        playSoundPacket.setVolume( volume );
        playSoundPacket.setPitch( pitch );
        this.playerConnection.sendPacket( playSoundPacket );
    }

    public void updateAttributes() {
        UpdateAttributesPacket updateAttributesPacket = null;
        for ( Attribute attribute : this.getAttributes() ) {
            if ( attribute.isDirty() ) {
                if ( updateAttributesPacket == null ) {
                    updateAttributesPacket = new UpdateAttributesPacket();
                    updateAttributesPacket.setRuntimeEntityId( this.entityId );
                }
                updateAttributesPacket.getAttributes().add( attribute.toNetwork() );
            }
        }

        if ( updateAttributesPacket != null ) {
            updateAttributesPacket.setTick( this.server.getCurrentTick() );
            this.playerConnection.sendPacket( updateAttributesPacket );
        }
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation( Location respawnLocation ) {
        this.respawnLocation = respawnLocation;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
        setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.Type.PLAYER_SPAWN );
        setSpawnPositionPacket.setDimensionId( this.spawnLocation.getDimension().ordinal() );
        setSpawnPositionPacket.setSpawnPosition( spawnLocation.toVector3i() );
        setSpawnPositionPacket.setBlockPosition( this.location.getWorld().getSpawnLocation().toVector3i() );
        this.playerConnection.sendPacket( setSpawnPositionPacket );
    }

    public int getInAirTicks() {
        return this.inAirTicks;
    }

    public void setInAirTicks( int inAirTicks ) {
        this.inAirTicks = inAirTicks;
    }

    public float getHighestPosition() {
        return this.highestPosition;
    }

    public void setHighestPosition( float highestPosition ) {
        this.highestPosition = highestPosition;
    }

    public float getFlySpeed() {
        return this.adventureSettings.getFlySpeed();
    }

    public void setFlySpeed( float value ) {
        this.adventureSettings.setFlySpeed( value );
        this.adventureSettings.update();
    }

    public float getWalkSpeed() {
        return this.adventureSettings.getWalkSpeed();
    }

    public void setWalkSpeed( float value ) {
        this.adventureSettings.setWalkSpeed( value );
        this.adventureSettings.update();
    }

    @Override
    public void setSkin( Skin skin ) {
        super.setSkin( skin );

        PlayerChangeSkinEvent playerChangeSkinEvent = new PlayerChangeSkinEvent( this, skin );
        if ( playerChangeSkinEvent.isCancelled() ) return;
        Server.getInstance().addToTabList( this.uuid, this.entityId, this.name, this.deviceInfo, this.getXuid(), this.skin );
    }

    public void sendServerSettings( Player player ) {
        if ( this.serverSettingsForm != -1 ) {
            Form<?> form = this.forms.get( this.serverSettingsForm );

            ServerSettingsResponsePacket response = new ServerSettingsResponsePacket();
            response.setFormId( this.serverSettingsForm );
            response.setFormData( form.toJSON().toJSONString() );
            player.getPlayerConnection().sendPacket( response );
        }
    }

    public <R> FormListener<R> showForm( Form<R> form ) {
        int formId = this.formId++;
        this.forms.put( formId, form );
        FormListener<R> formListener = new FormListener<R>();
        this.formListeners.put( formId, formListener );

        String json = form.toJSON().toJSONString();
        ModalFormRequestPacket packetModalRequest = new ModalFormRequestPacket();
        packetModalRequest.setFormId( formId );
        packetModalRequest.setFormData( json );
        this.getPlayerConnection().sendPacket( packetModalRequest );

        //Dirty fix to show image on buttonlist
        Server.getInstance().getScheduler().scheduleDelayed( () -> this.setAttributes( AttributeType.PLAYER_LEVEL, this.getLevel() ), 5 );
        return formListener;
    }

    public <R> FormListener<R> setSettingsForm( Form<R> form ) {
        if ( this.serverSettingsForm != -1 ) {
            this.removeSettingsForm();
        }

        int formId = this.formId++;
        this.forms.put( formId, form );

        FormListener<R> formListener = new FormListener<R>();
        this.formListeners.put( formId, formListener );
        this.serverSettingsForm = formId;
        return formListener;
    }

    public void removeSettingsForm() {
        if ( this.serverSettingsForm != -1 ) {
            this.forms.remove( this.serverSettingsForm );
            this.formListeners.remove( this.serverSettingsForm );
            this.serverSettingsForm = -1;
        }
    }

    public void parseGUIResponse( int formId, String json ) {
        // Get the listener and the form
        Form<?> form = this.forms.get( formId );
        if ( form != null ) {
            // Get listener
            FormListener formListener = this.formListeners.get( formId );

            if ( this.serverSettingsForm != formId ) {
                this.forms.remove( formId );
                this.formListeners.remove( formId );
            }

            if ( json == null || json.equals( "null" ) ) {
                formListener.getCloseConsumer().accept( null );
            } else {
                Object resp = form.parseResponse( json );
                if ( resp == null ) {
                    formListener.getCloseConsumer().accept( null );
                } else {
                    formListener.getResponseConsumer().accept( resp );
                }
            }
        }
    }

    public void addNpcDialogueForm( NpcDialogueForm npcDialogueForm ) {
        this.npcDialogueForms.add( npcDialogueForm );
    }

    public void removeNpcDialogueForm( NpcDialogueForm npcDialogueForm ) {
        this.npcDialogueForms.remove( npcDialogueForm );
    }

    public Set<NpcDialogueForm> getOpenNpcDialogueForms() {
        return new HashSet<>( this.npcDialogueForms );
    }

    public EntityFishingHook getEntityFishingHook() {
        return this.entityFishingHook;
    }

    public void setEntityFishingHook( EntityFishingHook entityFishingHook ) {
        this.entityFishingHook = entityFishingHook;
    }

    @Override
    public void setExperience( float value ) {
        PlayerExperienceChangeEvent playerExperienceChangeEvent = new PlayerExperienceChangeEvent( this, (int) this.getExperience(), (int) this.getLevel(), (int) value, 0 );
        if ( playerExperienceChangeEvent.isCancelled() ) {
            return;
        }
        super.setExperience( playerExperienceChangeEvent.getNewExperience() );
    }

    @Override
    public void setLevel( float value ) {
        PlayerExperienceChangeEvent playerExperienceChangeEvent = new PlayerExperienceChangeEvent( this, (int) this.getExperience(), (int) this.getLevel(), 0, (int) value );
        if ( playerExperienceChangeEvent.isCancelled() ) {
            return;
        }
        super.setExperience( playerExperienceChangeEvent.getNewLevel() );
    }

    public LoginData getLoginData() {
        return this.playerConnection.getLoginData();
    }

    // =========== Damage ===========

    @Override
    public boolean damage( EntityDamageEvent event ) {
        if ( this.adventureSettings.get( AdventureSettings.Type.ALLOW_FLIGHT ) && event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ) {
            return false;
        }
        return !this.gameMode.equals( GameMode.CREATIVE ) && !this.gameMode.equals( GameMode.SPECTATOR ) && super.damage( event );
    }

    @Override
    public float applyArmorReduction( EntityDamageEvent event, boolean damageArmor ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ||
                event.getDamageSource().equals( EntityDamageEvent.DamageSource.VOID ) ||
                event.getDamageSource().equals( EntityDamageEvent.DamageSource.DROWNING ) ) {
            return event.getDamage();
        }
        float damage = event.getDamage();
        float totalArmorValue = this.armorInventory.getTotalArmorValue();

        if ( damageArmor ) {
            this.armorInventory.damageEvenly( damage );
        }
        return -damage * totalArmorValue * 0.04f;
    }

    @Override
    public float applyFeatherFallingReduction( EntityDamageEvent event, float damage ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ) {
            Item boots = this.armorInventory.getBoots();
            if ( boots != null && !boots.getType().equals( ItemType.AIR ) ) {
                Enchantment enchantment = boots.getEnchantment( EnchantmentType.FEATHER_FALLING );
                if ( enchantment != null ) {
                    int featherFallingReduction = 12 * enchantment.getLevel();
                    return -( damage * ( featherFallingReduction / 100f ) );
                }
            }
        }
        return event.getDamage();
    }

    @Override
    public float applyProtectionReduction( EntityDamageEvent event, float damage ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.ENTITY_ATTACK ) ) {
            float protectionReduction = this.getProtectionReduction();
            return -( damage * ( protectionReduction / 100f ) );
        }
        return event.getDamage();
    }

    @Override
    public float applyProjectileProtectionReduction( EntityDamageEvent event, float damage ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.PROJECTILE ) ) {
            float protectionReduction = this.getProjectileProtectionReduction();
            return -( damage * ( protectionReduction / 100f ) );
        }
        return event.getDamage();
    }

    @Override
    public float applyFireProtectionReduction( EntityDamageEvent event, float damage ) {
        if ( event.getDamageSource().equals( EntityDamageEvent.DamageSource.ON_FIRE ) ) {
            float protectionReduction = this.getFireProtectionReduction();
            return -( damage * ( protectionReduction / 100f ) );
        }
        return event.getDamage();
    }

    private float getProtectionReduction() {
        float protectionReduction = 0;
        for ( Item content : this.armorInventory.getContents() ) {
            if ( content instanceof ItemArmor ) {
                Enchantment enchantment = content.getEnchantment( EnchantmentType.PROTECTION );
                if ( enchantment != null ) {
                    protectionReduction += 4 * enchantment.getLevel();
                }
            }
        }
        return protectionReduction;
    }

    private float getProjectileProtectionReduction() {
        float protectionReduction = 0;
        for ( Item content : this.armorInventory.getContents() ) {
            if ( content instanceof ItemArmor ) {
                Enchantment enchantment = content.getEnchantment( EnchantmentType.PROJECTILE_PROTECTION );
                if ( enchantment != null ) {
                    protectionReduction += enchantment.getLevel() * 8;
                }
            }
        }
        return protectionReduction;
    }

    private float getFireProtectionReduction() {
        float protectionReduction = 0;
        for ( Item content : this.armorInventory.getContents() ) {
            if ( content instanceof ItemArmor ) {
                Enchantment enchantment = content.getEnchantment( EnchantmentType.FIRE_PROTECTION );
                if ( enchantment != null ) {
                    protectionReduction += 15 * enchantment.getLevel();
                }
            }
        }
        return protectionReduction;
    }

    public void kill() {
        this.damage( new EntityDamageEvent( this, this.getMaxHealth(), EntityDamageEvent.DamageSource.COMMAND ) );
    }

    @Override
    protected void killInternal() {
        if ( !this.isDead ) {
            super.killInternal();
            EntityEventPacket entityEventPacket = new EntityEventPacket();
            entityEventPacket.setRuntimeEntityId( this.entityId );
            entityEventPacket.setType( EntityEventType.DEATH );
            this.playerConnection.sendPacket( entityEventPacket );

            this.fallDistance = 0;
            this.highestPosition = 0;
            this.inAirTicks = 0;

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
                case MAGIC_EFFECT -> this.getNameTag() + " was killed by magic";
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

            this.respawnLocation = this.getWorld().getSpawnLocation().add( 0, this.getEyeHeight(), 0 );

            RespawnPacket respawnPositionPacket = new RespawnPacket();
            respawnPositionPacket.setRuntimeEntityId( this.entityId );
            respawnPositionPacket.setState( RespawnPacket.State.SERVER_SEARCHING );
            respawnPositionPacket.setPosition( this.respawnLocation.toVector3f() );
            this.playerConnection.sendPacket( respawnPositionPacket );

            if ( playerDeathEvent.getDeathScreenMessage() != null && !playerDeathEvent.getDeathScreenMessage().isEmpty() ) {
                DeathInfoPacket deathInfoPacket = new DeathInfoPacket();
                deathInfoPacket.getMessageList().add( this.getName() );
                deathInfoPacket.setCauseAttackName( playerDeathEvent.getDeathScreenMessage() ); //#1e7dfc
                this.playerConnection.sendPacket( deathInfoPacket );
            }
        }
        this.lastDamageSource = EntityDamageEvent.DamageSource.COMMAND;
    }

    public boolean attackWithItemInHand( Entity target ) {
        if ( target instanceof EntityLiving living ) {
            boolean success = false;
            float damage = this.getAttackDamage();

            if ( this.hasEffect( EffectType.STRENGTH ) ) {
                damage = damage * 0.3f * ( this.getEffect( EffectType.STRENGTH ).getAmplifier() + 1 );
            }

            if ( this.hasEffect( EffectType.WEAKNESS ) ) {
                damage = -( damage * 0.2f * this.getEffect( EffectType.WEAKNESS ).getAmplifier() + 1 );
            }

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
                if ( success = living.damage( new EntityDamageByEntityEvent( living, this, damage, EntityDamageEvent.DamageSource.ENTITY_ATTACK ) ) ) {
                    if ( knockbackLevel > 0 ) {
                        Vector targetVelocity = target.getVelocity();
                        living.setVelocity( targetVelocity.add(
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
            if ( this.gameMode.equals( GameMode.SURVIVAL ) ) {
                this.exhaust( 0.3f );
            }
            return success;
        }
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
            this.armorInventory.sendContents( this );

            EntityEventPacket entityEventPacket = new EntityEventPacket();
            entityEventPacket.setRuntimeEntityId( this.entityId );
            entityEventPacket.setType( EntityEventType.RESPAWN );
            Server.getInstance().broadcastPacket( entityEventPacket );

            this.playerInventory.getItemInHand().addToHand( this );

            this.isDead = false;
        }
    }

    public List<Item> getDrops() {
        List<Item> drops = new ArrayList<>();
        for ( Item content : this.playerInventory.getContents() ) {
            if ( content != null && !( content.getType().equals( ItemType.AIR ) ) ) {
                if ( content.getEnchantment( EnchantmentType.CURSE_OF_VANISHING ) == null ) {
                    drops.add( content );
                }
            }
        }
        for ( Item content : this.cursorInventory.getContents() ) {
            if ( content != null && !( content.getType().equals( ItemType.AIR ) ) ) {
                if ( content.getEnchantment( EnchantmentType.CURSE_OF_VANISHING ) == null ) {
                    drops.add( content );
                }
            }
        }
        for ( Item content : this.armorInventory.getContents() ) {
            if ( content != null && !( content.getType().equals( ItemType.AIR ) ) ) {
                if ( content.getEnchantment( EnchantmentType.CURSE_OF_VANISHING ) == null ) {
                    drops.add( content );
                }
            }
        }
        return drops;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof Player player ) {
            return player.getEntityId() == this.entityId && player.getUUID().equals( this.uuid );
        }
        return false;
    }
}
