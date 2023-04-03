package org.jukeboxmc.entity;

import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockWater;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.event.entity.*;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    protected long entityId;

    protected final Metadata metadata;
    protected Location location;
    protected Location lastLocation;
    protected Vector velocity;
    protected Vector lastVector;
    protected final AxisAlignedBB boundingBox;

    protected boolean onGround;
    protected boolean closed;
    protected boolean isDead;

    protected int age;
    protected float fallDistance = 0;
    protected long fireTicks = 0;

    private final Set<Long> spawnedFor = new HashSet<>();

    public Entity() {
        this.entityId = Entity.entityCount++;
        this.metadata = new Metadata();
        this.metadata.setInt( EntityDataTypes.PLAYER_INDEX, 0 );
        this.metadata.setShort( EntityDataTypes.AIR_SUPPLY, (short) 400 );
        this.metadata.setShort( EntityDataTypes.AIR_SUPPLY_MAX, (short) 400 );
        this.metadata.setFloat( EntityDataTypes.SCALE, 1 );
        this.metadata.setFloat( EntityDataTypes.WIDTH, this.getWidth() );
        this.metadata.setFloat( EntityDataTypes.HEIGHT, this.getHeight() );
        this.metadata.setFlag( EntityFlag.HAS_GRAVITY, true );
        this.metadata.setFlag( EntityFlag.HAS_COLLISION, true );
        this.metadata.setFlag( EntityFlag.CAN_CLIMB, true );
        this.metadata.setFlag( EntityFlag.BREATHING, true );

        this.location = Server.getInstance().getDefaultWorld().getSpawnLocation();
        this.lastLocation = Server.getInstance().getDefaultWorld().getSpawnLocation();
        this.velocity = new Vector( 0, 0, 0, this.location.getDimension() );
        this.lastVector = new Vector( 0, 0, 0, this.location.getDimension() );
        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalculateBoundingBox();
    }

    public static <T extends Entity> T create( EntityType entityType ) {
        try {
            return (T) EntityRegistry.getEntityClass( entityType ).getConstructor().newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public void update( long currentTick ) {
        this.age++;

        if ( this.fireTicks > 0 ) {
            if ( this.fireTicks % 20 == 0 ) {
                this.damage( new EntityDamageEvent( this, 1, EntityDamageEvent.DamageSource.ON_FIRE ) );
            }
            this.fireTicks--;
            if ( this.fireTicks == 0 ) {
                this.setBurning( false );
            }
        }
    }

    public abstract String getName();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract EntityType getType();

    public abstract Identifier getIdentifier();

    public float getEyeHeight() {
        return this.getHeight() / 2 + 0.1f;
    }

    public BedrockPacket createSpawnPacket() {
        AddEntityPacket addEntityPacket = new AddEntityPacket();
        addEntityPacket.setRuntimeEntityId( this.entityId );
        addEntityPacket.setUniqueEntityId( this.entityId );
        addEntityPacket.setIdentifier( this.getIdentifier().getFullName() );
        addEntityPacket.setPosition( this.location.add( 0, this.getEyeHeight(), 0 ).toVector3f() );
        addEntityPacket.setMotion( this.velocity.toVector3f() );
        addEntityPacket.setRotation( Vector2f.from( this.location.getPitch(), this.location.getYaw() ) );
        addEntityPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        return addEntityPacket;
    }

    public boolean canCollideWith( Entity entity ) {
        return false;
    }

    public boolean canPassThrough() {
        return true;
    }

    protected void fall() {

    }

    public void interact( Player player, Vector vector ) {

    }

    public Entity spawn( Player player ) {
        if ( !this.spawnedFor.contains( player.getEntityId() ) ) {
            EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent( this );
            Server.getInstance().getPluginManager().callEvent( entitySpawnEvent );
            if ( entitySpawnEvent.isCancelled() ) {
                return this;
            }
            Entity entity = entitySpawnEvent.getEntity();
            this.getWorld().addEntity( entity );
            this.getChunk().addEntity( entity );
            player.getPlayerConnection().sendPacket( entity.createSpawnPacket() );
            this.spawnedFor.add( player.getEntityId() );
        }
        return this;
    }

    public Entity spawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.spawn( player );
        }
        return this;
    }

    public Entity despawn( Player player ) {
        if ( this.spawnedFor.contains( player.getEntityId() ) ) {
            EntityDespawnEvent entityDespawnEvent = new EntityDespawnEvent( this );
            Server.getInstance().getPluginManager().callEvent( entityDespawnEvent );
            if ( entityDespawnEvent.isCancelled() ) {
                return this;
            }
            RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
            removeEntityPacket.setUniqueEntityId( entityDespawnEvent.getEntity().getEntityId() );
            player.getPlayerConnection().sendPacket( removeEntityPacket );
            this.spawnedFor.remove( player.getEntityId() );
        }
        return this;
    }

    public Entity despawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.despawn( player );
        }
        return this;
    }

    public void close() {
        this.despawn();

        this.getChunk().removeEntity( this );
        this.getWorld().removeEntity( this );

        this.closed = true;
        this.isDead = true;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
        this.recalculateBoundingBox();
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity( Vector velocity, boolean sendVelocity ) {
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent( this, velocity );
        Server.getInstance().getPluginManager().callEvent( entityVelocityEvent );
        if ( entityVelocityEvent.isCancelled() ) {
            return;
        }
        this.velocity = entityVelocityEvent.getVelocity();
        if ( sendVelocity ) {
            SetEntityMotionPacket entityVelocityPacket = new SetEntityMotionPacket();
            entityVelocityPacket.setRuntimeEntityId( entityVelocityEvent.getEntity().getEntityId() );
            entityVelocityPacket.setMotion( entityVelocityEvent.getVelocity().toVector3f() );
            Server.getInstance().broadcastPacket( entityVelocityPacket );
        }
    }

    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
    }

    public Vector getLastVector() {
        return lastVector;
    }

    public void setLastVector( Vector lastVector ) {
        this.lastVector = lastVector;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public float getX() {
        return this.location.getX();
    }

    public float getY() {
        return this.location.getY();
    }

    public float getZ() {
        return this.location.getZ();
    }

    public int getBlockX() {
        return this.location.getBlockX();
    }

    public int getBlockY() {
        return this.location.getBlockY();
    }

    public int getBlockZ() {
        return this.location.getBlockZ();
    }

    public float getYaw() {
        return this.location.getYaw();
    }

    public void setYaw( float yaw ) {
        this.location.setYaw( yaw );
    }

    public float getPitch() {
        return this.location.getPitch();
    }

    public void setPitch( float pitch ) {
        this.location.setPitch( pitch );
    }

    public int getChunkX() {
        return this.location.getBlockX() >> 4;
    }

    public int getChunkZ() {
        return this.location.getBlockZ() >> 4;
    }

    public Chunk getChunk() {
        return this.location.getWorld().getChunk( this.location.getChunkX(), this.location.getChunkZ(), this.location.getDimension() );
    }

    public Chunk getLoadedChunk() {
        return this.location.getWorld().getLoadedChunk( this.location.getChunkX(), this.location.getChunkZ(), this.location.getDimension() );
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation( Location lastLocation ) {
        this.lastLocation = lastLocation;
    }

    public Dimension getDimension() {
        return this.location.getDimension();
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround( boolean onGround ) {
        this.onGround = onGround;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public int getAge() {
        return this.age;
    }

    public long getFireTicks() {
        return this.fireTicks;
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    public void recalculateBoundingBox() {
        float height = this.getHeight() * this.getScale();
        float radius = ( this.getWidth() * this.getScale() ) / 2;
        this.boundingBox.setBounds(
                this.location.getX() - radius,
                this.location.getY(),
                this.location.getZ() - radius,
                this.location.getX() + radius,
                this.location.getY() + height,
                this.location.getZ() + radius );

        this.metadata.setFloat( EntityDataTypes.HEIGHT, this.getHeight() );
        this.metadata.setFloat( EntityDataTypes.WIDTH, this.getWidth() );
    }

    public Direction getDirection() {
        double rotation = this.location.getYaw() % 360;
        if ( rotation < 0 ) {
            rotation += 360.0;
        }

        if ( 45 <= rotation && rotation < 135 ) {
            return Direction.WEST;
        } else if ( 135 <= rotation && rotation < 225 ) {
            return Direction.NORTH;
        } else if ( 225 <= rotation && rotation < 315 ) {
            return Direction.EAST;
        } else {
            return Direction.SOUTH;
        }
    }

    //============ Metadata =============

    public short getMaxAirSupply() {
        return this.metadata.getShort( EntityDataTypes.AIR_SUPPLY );
    }

    public void setMaxAirSupply( short value ) {
        if ( value != this.getMaxAirSupply() ) {
            this.updateMetadata( this.metadata.setShort( EntityDataTypes.AIR_SUPPLY, value ) );
        }
    }

    public float getScale() {
        return this.metadata.getFloat( EntityDataTypes.SCALE );
    }

    public void setScale( float value ) {
        if ( value != this.getScale() ) {
            this.updateMetadata( this.metadata.setFloat( EntityDataTypes.SCALE, value ) );
        }
    }

    public boolean hasCollision() {
        return this.metadata.getFlag( EntityFlag.HAS_COLLISION );
    }

    public void setCollision( boolean value ) {
        if ( this.hasCollision() != value ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.HAS_COLLISION, value ) );
        }
    }

    public boolean hasGravity() {
        return this.metadata.getFlag( EntityFlag.HAS_GRAVITY );
    }

    public void setGravity( boolean value ) {
        if ( this.hasGravity() != value ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.HAS_GRAVITY, value ) );
        }
    }

    public String getNameTag() {
        return this.metadata.getString( EntityDataTypes.NAME );
    }

    public void setNameTag( String value ) {
        this.updateMetadata( this.metadata.setString( EntityDataTypes.NAME, value ) );
    }

    public boolean isNameTagVisible() {
        return this.metadata.getFlag( EntityFlag.CAN_SHOW_NAME );
    }

    public void setNameTagVisible( boolean value ) {
        if ( value != this.isNameTagVisible() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.CAN_SHOW_NAME, value ) );
        }
    }

    public boolean isNameTagAlwaysVisible() {
        return this.metadata.getFlag( EntityFlag.ALWAYS_SHOW_NAME );
    }

    public void setNameTagAlwaysVisible( boolean value ) {
        if ( value != this.isNameTagAlwaysVisible() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.ALWAYS_SHOW_NAME, value ) );
        }
    }

    public boolean canClimb() {
        return this.metadata.getFlag( EntityFlag.CAN_CLIMB );
    }

    public void setCanClimb( boolean value ) {
        if ( value != this.canClimb() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.CAN_CLIMB, value ) );
        }
    }

    public boolean isInvisible() {
        return this.metadata.getFlag( EntityFlag.INVISIBLE );
    }

    public void setInvisible( boolean value ) {
        if ( value != this.isInvisible() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.INVISIBLE, value ) );
        }
    }

    public boolean isBurning() {
        return this.metadata.getFlag( EntityFlag.ON_FIRE );
    }

    public void setBurning( boolean value ) {
        if ( value != this.isBurning() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.ON_FIRE, value ) );
        }
    }

    public void setBurning( long value, TimeUnit timeUnit ) {
        int newFireTicks = (int) ( timeUnit.toMillis( value ) / 50 );
        if ( newFireTicks > this.fireTicks ) {
            this.fireTicks = newFireTicks;
            this.setBurning( true );
        } else if ( newFireTicks == 0 ) {
            this.fireTicks = 0;
            this.setBurning( false );
        }
    }

    public boolean isImmobile() {
        return this.metadata.getFlag( EntityFlag.NO_AI );
    }

    public void setImmobile( boolean value ) {
        if ( value != this.isImmobile() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.NO_AI, value ) );
        }
    }

    public void updateMetadata( Metadata metadata ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setRuntimeEntityId( this.entityId );
        setEntityDataPacket.getMetadata().putAll( metadata.getEntityDataMap() );
        setEntityDataPacket.setTick( Server.getInstance().getCurrentTick() );
        Server.getInstance().broadcastPacket( setEntityDataPacket );
    }

    public void updateMetadata() {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setRuntimeEntityId( this.entityId );
        setEntityDataPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        setEntityDataPacket.setTick( Server.getInstance().getCurrentTick() );
        Server.getInstance().broadcastPacket( setEntityDataPacket );
    }

    protected boolean isOnLadder() {
        Location location = this.getLocation();
        Chunk loadedChunk = location.getWorld().getLoadedChunk( location.getChunkX(), location.getChunkZ(), location.getDimension() );
        if ( loadedChunk == null ) {
            return false;
        }
        Block block = loadedChunk.getBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0 );
        return block.getType().equals( BlockType.LADDER );
    }

    public boolean isInWater() {
        Vector eyeLocation = this.location.add( 0, this.getEyeHeight(), 0 );
        Chunk loadedChunk = this.getWorld().getLoadedChunk( eyeLocation.getChunkX(), eyeLocation.getChunkZ(), this.location.getDimension() );
        if ( loadedChunk == null ) {
            return false;
        }


        Block block = loadedChunk.getBlock( eyeLocation.getBlockX(), eyeLocation.getBlockY(), eyeLocation.getBlockZ(), 0 );
        if ( block.getType().equals( BlockType.WATER ) || block.getType().equals( BlockType.FLOWING_WATER )) {
            float yLiquid = (float) ( ( block.getLocation().getY() + 1 + ( (BlockWater) block ).getLiquidDepth() - 0.12 ) );
            return eyeLocation.getY() < yLiquid;
        }

        return false;
    }

    public void setOnFire( int value, TimeUnit timeUnit ) {
        long ticks = timeUnit.toMillis( value ) / 50;
        if ( ticks > this.fireTicks ) {
            this.fireTicks = ticks;
        }
    }

    public boolean isOnFire() {
        return this.fireTicks > 0;
    }

    public boolean damage( EntityDamageEvent event ) {
        if ( this.isDead ) {
            return false;
        }
        if ( event instanceof EntityDamageByEntityEvent ) {
            Entity damager = ( (EntityDamageByEntityEvent) event ).getDamager();
            if ( damager instanceof Player ) {
                return !( (Player) damager ).getGameMode().equals( GameMode.SPECTATOR );
            }
        }
        Server.getInstance().getPluginManager().callEvent( event );
        return !event.isCancelled();
    }
}
