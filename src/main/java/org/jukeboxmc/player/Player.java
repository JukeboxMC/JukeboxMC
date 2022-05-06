package org.jukeboxmc.player;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.command.CommandData;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.packet.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.CommandSender;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.event.entity.EntityHealEvent;
import org.jukeboxmc.event.inventory.InventoryCloseEvent;
import org.jukeboxmc.event.inventory.InventoryOpenEvent;
import org.jukeboxmc.event.player.PlayerDeathEvent;
import org.jukeboxmc.event.player.PlayerRespawnEvent;
import org.jukeboxmc.form.Form;
import org.jukeboxmc.form.FormListener;
import org.jukeboxmc.inventory.*;
import org.jukeboxmc.inventory.transaction.CraftingTransaction;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.enchantment.EnchantmentKnockback;
import org.jukeboxmc.item.enchantment.EnchantmentSharpness;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.world.Difficulty;
import org.jukeboxmc.world.Sound;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkLoader;
import org.jukeboxmc.math.Vector;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player extends EntityHuman implements ChunkLoader, CommandSender, InventoryHolder {

    private final PlayerConnection playerConnection;

    private Location spawnLocation;
    private GameMode gameMode;
    private final Server server;
    private final AdventureSettings adventureSettings;
    private CraftingTransaction craftingTransaction;

    private ContainerInventory currentInventory;
    private final CraftingTableInventory craftingTableInventory;
    private final CursorInventory cursorInventory;
    private final CartographyTableInventory cartographyTableInventory;
    private final SmithingTableInventory smithingTableInventory;
    private final AnvilInventory anvilInventory;
    private final EnderChestInventory enderChestInventory;
    private final StoneCutterInventory stoneCutterInventory;
    private final GrindstoneInventory grindstoneInventory;

    private EntityFishingHook entityFishingHook;

    private Location respawnLocation = null;

    private long lastBreakTime = 0;
    private Vector lasBreakPosition;
    private boolean breakingBlock = false;

    private int viewDistance = 8;

    private final Map<UUID, Set<String>> permissions = new HashMap<>();

    private int formId;
    private int serverSettingsForm = -1;
    private final Int2ObjectMap<Form<?>> forms = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectMap<FormListener<?>> formListeners = new Int2ObjectOpenHashMap<>();

    public Player( PlayerConnection playerConnection ) {
        this.playerConnection = playerConnection;
        this.spawnLocation = this.location.getWorld().getSpawnLocation();
        this.gameMode = playerConnection.getServer().getGameMode();
        this.server = playerConnection.getServer();
        this.adventureSettings = new AdventureSettings( this );

        this.cursorInventory = new CursorInventory( this, this.entityId );
        this.craftingTableInventory = new CraftingTableInventory( this );
        this.cartographyTableInventory = new CartographyTableInventory( this );
        this.smithingTableInventory = new SmithingTableInventory( this );
        this.anvilInventory = new AnvilInventory( this );
        this.enderChestInventory = new EnderChestInventory( this );
        this.stoneCutterInventory = new StoneCutterInventory( this );
        this.grindstoneInventory = new GrindstoneInventory( this );

        this.lasBreakPosition = new Vector( 0, 0, 0 );
    }

    @Override
    public void update( long currentTick ) {
        if ( !this.playerConnection.isLoggedIn() ) {
            return;
        }
        super.update( currentTick );

        Collection<Entity> nearbyEntities = this.getWorld().getNearbyEntities( this.getBoundingBox().grow( 1, 0.5f, 1 ), this.dimension, null );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                nearbyEntity.onCollideWithPlayer( this );
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

    @Override
    public Server getServer() {
        return this.server;
    }

    public boolean isSpawned() {
        return this.playerConnection.isSpawned();
    }

    public boolean isClosed() {
        return this.playerConnection.isClosed();
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

    public String getXuid() {
        return this.playerConnection.getLoginData().getXuid();
    }

    public AdventureSettings getAdventureSettings() {
        return this.adventureSettings;
    }

    public ContainerInventory getCurrentInventory() {
        return this.currentInventory;
    }

    public CraftingTableInventory getCraftingTableInventory() {
        return this.craftingTableInventory;
    }

    public CursorInventory getCursorInventory() {
        return this.cursorInventory;
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

    public Inventory getInventory( WindowId windowIdById, int slot ) {
        return switch ( windowIdById ) {
            case PLAYER -> this.getInventory();
            case CURSOR_DEPRECATED -> this.getCursorInventory();
            case ARMOR_DEPRECATED -> this.getArmorInventory();
            default -> this.getCurrentInventory();
        };
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;

        SetSpawnPositionPacket setSpawnPositionPacket = new SetSpawnPositionPacket();
        setSpawnPositionPacket.setSpawnType( SetSpawnPositionPacket.Type.PLAYER_SPAWN );
        setSpawnPositionPacket.setDimensionId( this.dimension.ordinal() );
        setSpawnPositionPacket.setSpawnPosition( spawnLocation.toVector3i() );
        setSpawnPositionPacket.setBlockPosition( this.location.getWorld().getSpawnLocation().toVector3i() );
        this.playerConnection.sendPacket( setSpawnPositionPacket );
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode( GameMode gameMode ) {
        this.gameMode = gameMode;

        SetPlayerGameTypePacket setPlayerGameTypePacket = new SetPlayerGameTypePacket();
        setPlayerGameTypePacket.setGamemode( gameMode.getId() );
        this.playerConnection.sendPacket( setPlayerGameTypePacket );
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance( int viewDistance ) {
        this.viewDistance = viewDistance;

        ChunkRadiusUpdatedPacket chunkRadiusUpdatedPacket = new ChunkRadiusUpdatedPacket();
        chunkRadiusUpdatedPacket.setRadius( viewDistance );
        this.playerConnection.sendPacket( chunkRadiusUpdatedPacket );
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation( Location respawnLocation ) {
        this.respawnLocation = respawnLocation;
    }

    public void sendEntityData() {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setRuntimeEntityId( this.entityId );
        setEntityDataPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        setEntityDataPacket.setTick( this.server.getCurrentTick() );
        this.sendPacket( setEntityDataPacket );
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

    public void teleport( Location location, MovePlayerPacket.Mode mode ) {
        World currentWorld = this.getWorld();
        World world = location.getWorld();

        if ( currentWorld != world ) {
            this.despawn();
            currentWorld.getPlayers().forEach( player -> player.despawn( this ) );

            this.getChunk().removeEntity( this );
            currentWorld.removeEntity( this );

            this.playerConnection.resetChunks();

            this.setLocation( location );
            this.playerConnection.needNewChunks();

            world.addEntity( this );
            this.getChunk().addEntity( this );
            this.spawn();
            world.getPlayers().forEach( player -> player.spawn( this ) );
            this.movePlayer( location, mode );
            return;
        }

        this.setLocation( location );
        this.movePlayer( location, mode );
    }

    public void teleport( Location location ) {
        this.teleport( location, MovePlayerPacket.Mode.TELEPORT );
    }

    public void teleport( Player player ) {
        this.teleport( player.getLocation() );
    }

    public void movePlayer( Location location, MovePlayerPacket.Mode mode ) {
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.setRuntimeEntityId( this.entityId );
        movePlayerPacket.setPosition( location.toVector3f() );
        movePlayerPacket.setRotation( Vector3f.from( location.getPitch(), location.getYaw(), location.getYaw() ) );
        movePlayerPacket.setMode( mode );
        if ( mode == MovePlayerPacket.Mode.TELEPORT ) {
            movePlayerPacket.setTeleportationCause( MovePlayerPacket.TeleportationCause.BEHAVIOR );
        }
        movePlayerPacket.setOnGround( this.onGround );
        movePlayerPacket.setRidingRuntimeEntityId( 0 );
        movePlayerPacket.setTick( this.server.getCurrentTick() );
        this.sendPacket( movePlayerPacket );
    }

    public void movePlayer( Player player, MovePlayerPacket.Mode mode ) {
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.setRuntimeEntityId( player.getEntityId() );
        movePlayerPacket.setPosition( player.getLocation().toVector3f().add( 0, player.getEyeHeight(), 0 ) );
        movePlayerPacket.setRotation( Vector3f.from( player.getLocation().getPitch(), player.getLocation().getYaw(), player.getLocation().getYaw() ) );
        movePlayerPacket.setMode( mode );
        if ( mode == MovePlayerPacket.Mode.TELEPORT ) {
            movePlayerPacket.setTeleportationCause( MovePlayerPacket.TeleportationCause.BEHAVIOR );
        }
        movePlayerPacket.setOnGround( player.isOnGround() );
        movePlayerPacket.setRidingRuntimeEntityId( 0 );
        movePlayerPacket.setTick( this.server.getCurrentTick() );
        this.sendPacket( movePlayerPacket );
    }

    @Override
    public void sendMessage( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.RAW );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.sendPacket( textPacket );
    }

    public void sendTip( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.TIP );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.sendPacket( textPacket );
    }

    public void sendPopup( String text ) {
        TextPacket textPacket = new TextPacket();
        textPacket.setType( TextPacket.Type.POPUP );
        textPacket.setMessage( text );
        textPacket.setNeedsTranslation( false );
        textPacket.setXuid( this.getXuid() );
        textPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        this.sendPacket( textPacket );
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
            this.sendCommandData();
        }
    }

    public void removePermissions( Collection<String> permissions ) {
        if ( this.permissions.containsKey( this.uuid ) ) {
            this.permissions.get( this.uuid ).removeAll( permissions );
            this.sendCommandData();
        }
    }

    public boolean isOp() {
        return this.adventureSettings.get( AdventureSettings.Type.OPERATOR );
    }

    public void setOp( boolean value ) {
        this.sendCommandData();
        this.adventureSettings.set( AdventureSettings.Type.OPERATOR, value );
        this.adventureSettings.update();
        if ( value ) {
            this.server.addOperatorToFile( this.getName() );
        } else {
            this.server.removeOperatorFromFile( this.getName() );
        }
        this.sendCommandData();
    }

    public void sendChunk( Chunk chunk ) {
        this.playerConnection.sendChunk( chunk );
    }

    @Override
    public void chunkLoadCallback( Chunk chunk, boolean success ) {
        if ( success ) {
            this.sendChunk( chunk );
        }
    }

    @Override
    public void chunkGenerationCallback( Chunk chunk ) {
        this.sendChunk( chunk );
    }

    public boolean isChunkLoaded( int chunkX, int chunkZ ) {
        return this.playerConnection.isChunkLoaded( chunkX, chunkZ );
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
        this.sendPacket( availableCommandsPacket );
    }

    public void openInventory( ContainerInventory inventory, Vector position, byte windowId ) {
        InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent( inventory, this );
        Server.getInstance().getPluginManager().callEvent( inventoryOpenEvent );
        if ( inventoryOpenEvent.isCancelled() ) {
            return;
        }

        if ( this.currentInventory != null ) {
            this.closeInventory( this.currentInventory );
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
            this.closeInventory( WindowId.OPEN_CONTAINER.getId(), true );
        }
    }

    public void closeInventory( int windowId, boolean isServerSide ) {
        if ( this.currentInventory != null ) {
            this.currentInventory.removeViewer( this );

            ContainerClosePacket containerClosePacket = new ContainerClosePacket();
            containerClosePacket.setId( (byte) windowId );
            containerClosePacket.setUnknownBool0( isServerSide );
            this.playerConnection.sendPacket( containerClosePacket );

            Server.getInstance().getPluginManager().callEvent( new InventoryCloseEvent( this.currentInventory, this ) );

            this.currentInventory = null;
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
        playSoundPacket.setPosition( position.toVector3f() );
        playSoundPacket.setSound( sound.getSound() );
        playSoundPacket.setVolume( volume );
        playSoundPacket.setPitch( pitch );
        this.playerConnection.sendPacket( playSoundPacket );
    }

    public boolean attackWithItemInHand( Entity target ) {
        if ( target instanceof Player player ) {

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
                if ( success = player.damage( new EntityDamageByEntityEvent( player, this, damage, damageSource ) ) ) {
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
    public boolean damage( EntityDamageEvent event ) {
        if ( this.adventureSettings.get( AdventureSettings.Type.ALLOW_FLIGHT ) && event.getDamageSource().equals( EntityDamageEvent.DamageSource.FALL ) ) {
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
            entityEventPacket.setRuntimeEntityId( this.entityId );
            entityEventPacket.setType( EntityEventType.DEATH );
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

            this.respawnLocation = this.getWorld().getSpawnLocation().add( 0, this.getEyeHeight(), 0 );

            RespawnPacket respawnPositionPacket = new RespawnPacket();
            respawnPositionPacket.setRuntimeEntityId( this.entityId );
            respawnPositionPacket.setState( RespawnPacket.State.SERVER_SEARCHING );
            respawnPositionPacket.setPosition( this.respawnLocation.toVector3f() );
            this.playerConnection.sendPacket( respawnPositionPacket );
        }
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
            entityEventPacket.setRuntimeEntityId( this.entityId );
            entityEventPacket.setType( EntityEventType.RESPAWN );
            this.getWorld().sendDimensionPacket( entityEventPacket, this.dimension );

            this.playerInventory.getItemInHand().addToHand( this );

            this.isDead = false;
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

    public EntityFishingHook getEntityFishingHook() {
        return this.entityFishingHook;
    }

    public void setEntityFishingHook( EntityFishingHook entityFishingHook ) {
        this.entityFishingHook = entityFishingHook;
    }

    public void createCraftingTransaction( List<InventoryAction> inventoryTransactions ) {
        this.craftingTransaction = new CraftingTransaction( this, inventoryTransactions );
    }

    public CraftingTransaction getCraftingTransaction() {
        return this.craftingTransaction;
    }

    public void resetCraftingTransaction() {
        this.craftingTransaction = null;
    }

    public void sendServerSettings( Player player ) {
        if ( this.serverSettingsForm != -1 ) {
            Form<?> form = this.forms.get( this.serverSettingsForm );

            ServerSettingsResponsePacket response = new ServerSettingsResponsePacket();
            response.setFormId( this.serverSettingsForm );
            response.setFormData( form.toJSON().toJSONString() );
            player.sendPacket( response );
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
        this.sendPacket( packetModalRequest );
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

            if ( json.equals( "null" ) ) {
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

    public PlayerConnection getPlayerConnection() {
        return this.playerConnection;
    }

    public void sendPacket( BedrockPacket packet ) {
        this.playerConnection.sendPacket( packet );
    }


}
