package org.jukeboxmc.entity;

import org.jukeboxmc.JukeboxMC;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.event.entity.EntitySpawnEvent;
import org.jukeboxmc.event.entity.EntityVelocityEvent;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Particle;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT, NukkitX
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    protected long entityId = 0;
    protected long lastUpdate = 0;

    protected int ySize = 0;

    protected float fallDistance = 0;

    protected Location location;
    protected Location lastLocation;
    protected Vector velocity;
    protected Vector lastVelocity;

    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;
    protected Dimension dimension = Dimension.OVERWORLD;

    protected boolean justCreated = true;
    protected boolean isOnGround = false;
    protected boolean isCollidedVertically = false;
    protected boolean isCollidedHorizontally = false;
    protected boolean isCollided = false;

    protected Map<Long, Player> spawnedFor = new HashMap<>();

    public Entity() {
        this.metadata = new Metadata();
        this.metadata.setLong( MetadataFlag.INDEX, 0 );
        this.metadata.setShort( MetadataFlag.MAX_AIR_SUPPLY, (short) 400 );
        this.metadata.setFloat( MetadataFlag.SCALE, 1 );
        this.metadata.setFloat( MetadataFlag.BOUNDING_BOX_WIDTH, this.getWidth() );
        this.metadata.setFloat( MetadataFlag.BOUNDING_BOX_HEIGHT, this.getHeight() );
        this.metadata.setShort( MetadataFlag.AIR_SUPPLY, (short) 0 );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, true );

        this.location = new Location( Server.getInstance().getDefaultWorld(), 0, 7, 0, 0, 0 );
        this.location.setDimension( this.dimension );
        this.lastLocation = new Location( Server.getInstance().getDefaultWorld(), 0, 0, 0 );
        this.lastLocation.setDimension( this.dimension );

        this.velocity = new Vector( 0, 0, 0, this.dimension );
        this.lastVelocity = new Vector( 0, 0, 0, this.dimension );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalculateBoundingBox();
        this.incrementEntityId();
    }

    public abstract String getName();

    public abstract String getEntityType();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract Packet createSpawnPacket();

    public void update( long currentTick ) {
    }

    public void onCollideWithPlayer( Player player ) {
    }

    public Entity spawn( Player player ) {
        if ( !this.spawnedFor.containsKey( player.getEntityId() ) ) {
            EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent( this );
            Server.getInstance().getPluginManager().callEvent( entitySpawnEvent );
            if ( entitySpawnEvent.isCancelled() ) {
                return this;
            }
            Entity eventEntity = entitySpawnEvent.getEntity();
            eventEntity.setLocation( this.location );

            eventEntity.getWorld().addEntity( this );

            this.spawnedFor.put( player.getEntityId(), player );
            player.getPlayerConnection().sendPacket( eventEntity.createSpawnPacket() );
        }
        return this;
    }

    public Entity spawn() {
        for ( Entity entity : this.getChunk().getEntitys() ) {
            if ( entity instanceof Player ) {
                Player player = (Player) entity;
                this.spawn( player );
            }

        }
        return this;
    }

    public void despawn( Player player ) {
        if ( this.spawnedFor.containsKey( player.getEntityId() ) ) {
            RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
            removeEntityPacket.setEntityId( this.entityId );
            player.getPlayerConnection().sendPacket( removeEntityPacket );
            this.getWorld().removeEntity( this );
            this.spawnedFor.remove( player.getEntityId() );
        }
    }

    public void despawn() {
        for ( Player onlinePlayer : this.spawnedFor.values() ) {
            this.despawn( onlinePlayer );
        }
    }

    public float getGravity() {
        return 0.08f;
    }

    public float getEyeHeight() {
        return this.getHeight() / 2 + 0.1f;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return entityId;
    }

    public long incrementEntityId() {
        return this.entityId = Entity.entityCount++;
    }

    public Location getLocation() {
        return this.location;
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public Vector getLastVelocity() {
        return this.lastVelocity;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public float getX() {
        return this.getLocation().getX();
    }

    public int getFloorX() {
        return (int) Math.floor( this.getLocation().getX() );
    }

    public float getY() {
        return this.location.getY();
    }

    public int getFloorY() {
        return (int) Math.floor( this.getLocation().getY() );
    }

    public float getZ() {
        return this.location.getZ();
    }

    public int getFloorZ() {
        return (int) Math.floor( this.getLocation().getZ() );
    }

    public float getHeadYaw() {
        return this.location.getHeadYaw();
    }

    public float getYaw() {
        return this.location.getYaw();
    }

    public float getPitch() {
        return this.location.getPitch();
    }

    public int getChunkX() {
        return (int) this.location.getX() >> 4;
    }

    public int getChunkZ() {
        return (int) this.location.getZ() >> 4;
    }

    public Chunk getChunk() {
        return this.location.getWorld().getChunk( this.getChunkX(), this.getChunkZ(), this.dimension );
    }

    public void setLocation( Location location ) {
        this.location = location;
        this.recalculateBoundingBox();
    }

    public void setLastLocation( Location lastLocation ) {
        this.lastLocation = lastLocation;
    }

    public void setVelocity( Vector velocity, boolean sendVelocity ) {
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent( this, velocity );
        Server.getInstance().getPluginManager().callEvent( entityVelocityEvent );
        if ( entityVelocityEvent.isCancelled() ) {
            return;
        }
        this.velocity = entityVelocityEvent.getVelocity();
        if ( sendVelocity ) {
            EntityVelocityPacket entityVelocityPacket = new EntityVelocityPacket();
            entityVelocityPacket.setEntityId( entityVelocityEvent.getEntity().getEntityId() );
            entityVelocityPacket.setVelocity( entityVelocityEvent.getVelocity() );
            this.getWorld().sendDimensionPacket( entityVelocityPacket, velocity.getDimension() );
        }
    }

    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
    }

    public void setLastVelocity( Vector lastVelocity ) {
        this.lastVelocity = lastVelocity;
    }

    public void setX( float x ) {
        this.location.setX( x );
    }

    public void setY( float y ) {
        this.location.setY( y );
    }

    public void setZ( float z ) {
        this.location.setZ( z );
    }

    public void setHeadYaw( float headYaw ) {
        this.location.setHeadYaw( headYaw );
    }

    public void setYaw( float yaw ) {
        this.location.setYaw( yaw );
    }

    public void setPitch( float pitch ) {
        this.location.setPitch( pitch );
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
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

    public boolean isOnGround() {
        return this.isOnGround;
    }

    public void setOnGround( boolean onGround ) {
        this.isOnGround = onGround;
    }

    public String getNameTag() {
        return this.metadata.getString( MetadataFlag.NAMETAG );
    }

    public void setNameTag( String value ) {
        this.updateMetadata( this.metadata.setString( MetadataFlag.NAMETAG, value ) );
    }

    public boolean isNameTagVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG );
    }

    public void setNameTagVisible( boolean value ) {
        if ( value != this.isNameTagVisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG, value ) );
        }
    }

    public boolean isNameTagAlwaysVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG );
    }

    public void setNameTagAlwaysVisible( boolean value ) {
        if ( value != this.isNameTagAlwaysVisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG, value ) );
        }
    }

    public boolean canClimb() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB );
    }

    public void setCanClimb( boolean value ) {
        if ( value != this.canClimb() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, value ) );
        }
    }

    public boolean isInvisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.INVISIBLE );
    }

    public void setInvisible( boolean value ) {
        if ( value != this.isInvisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.INVISIBLE, value ) );
        }
    }

    public boolean isBurning() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.ON_FIRE );
    }

    public void setBurning( boolean value ) {
        if ( value != this.isBurning() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.ON_FIRE, value ) );
        }
    }

    public boolean isImmobile() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.IMMOBILE );
    }

    public void setImmobile( boolean value ) {
        if ( value != this.isImmobile() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.IMMOBILE, value ) );
        }
    }

    public float getScale() {
        return this.metadata.getFloat( MetadataFlag.SCALE );
    }

    public void setScale( float value ) {
        this.updateMetadata( this.metadata.setFloat( MetadataFlag.SCALE, value ) );
        this.recalculateBoundingBox();
    }

    public boolean hasCollision() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION );
    }

    public void setCollision( boolean value ) {
        if ( this.hasCollision() != value ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, value ) );
        }
    }

    public boolean hasGravity() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY );
    }

    public void setGravity( boolean value ) {
        if ( this.hasGravity() != value ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY, value ) );
        }
    }

    public void updateMetadata( Metadata metadata ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.entityId );
        setEntityDataPacket.setMetadata( metadata );
        setEntityDataPacket.setTick( 0 );
        for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
            onlinePlayer.getPlayerConnection().sendPacket( setEntityDataPacket );
        }
    }

    public void recalculateBoundingBox() {
        Location location = this.getLocation();
        this.boundingBox.setBounds(
                location.getX() - ( this.getWidth() / 2 ),
                location.getY(),
                location.getZ() - ( this.getWidth() / 2 ),
                location.getX() + ( this.getWidth() / 2 ),
                location.getY() + this.getEyeHeight(),
                location.getZ() + ( this.getWidth() / 2 )
        );
    }

    public void spawnParticle( Particle particle, Vector position ) {
        SpawnParticleEffectPacket spawnParticleEffectPacket = new SpawnParticleEffectPacket();
        spawnParticleEffectPacket.setParticle( particle );
        spawnParticleEffectPacket.setPosition( position );
        spawnParticleEffectPacket.setEntityId( this.entityId );
        spawnParticleEffectPacket.setDimension( Dimension.OVERWORLD ); //TODO
        for ( Player player : this.location.getWorld().getPlayers() ) {
            player.getPlayerConnection().sendPacket( spawnParticleEffectPacket );
        }
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public void updateMovement() {
        double diffPosition = ( this.getX() - this.lastLocation.getX() ) * ( this.getX() - this.lastLocation.getX() ) + ( this.getY() - this.lastLocation.getY() ) * ( this.getY() - this.lastLocation.getY() ) + ( this.getZ() - this.lastLocation.getZ() ) * ( this.getZ() - this.lastLocation.getZ() );
        double diffRotation = ( this.getYaw() - this.lastLocation.getYaw() ) * ( this.getYaw() - this.lastLocation.getYaw() ) + ( this.getPitch() - this.lastLocation.getPitch() ) * ( this.getPitch() - this.lastLocation.getPitch() );

        double diffMotion = ( this.velocity.getX() - this.lastVelocity.getX() ) * ( this.velocity.getX() - this.lastVelocity.getX() ) + ( this.velocity.getY() - this.lastVelocity.getY() ) * ( this.velocity.getY() - this.lastVelocity.getY() ) + ( this.velocity.getZ() - this.lastVelocity.getZ() ) * ( this.velocity.getZ() - this.lastVelocity.getZ() );

        if ( diffPosition > 0.0001 || diffRotation > 1.0 ) {
            this.lastLocation.setX( this.getX() );
            this.lastLocation.setY( this.getY() );
            this.lastLocation.setZ( this.getZ() );
            this.lastLocation.setYaw( this.getYaw() );
            this.lastLocation.setPitch( this.getPitch() );
            this.addMovement( this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), this.getYaw() );
        }
        if ( diffMotion > 0.0025 || ( diffMotion > 0.0001 && this.getVelocity().lengthSquared() <= 0.0001 ) ) { //0.05 ** 2
            this.lastVelocity.setX( this.velocity.getX() );
            this.lastVelocity.setY( this.velocity.getY() );
            this.lastVelocity.setZ( this.velocity.getZ() );
            this.setVelocity( this.velocity, true );
        }
    }

    public void addMovement( float x, float y, float z, float yaw, float pitch, float headYaw ) {
        EntityMovementPacket entityMovementPacket = new EntityMovementPacket();
        entityMovementPacket.setEntityId( this.entityId );
        entityMovementPacket.setX( x );
        entityMovementPacket.setY( y );
        entityMovementPacket.setZ( z );
        entityMovementPacket.setYaw( yaw );
        entityMovementPacket.setPitch( pitch );
        entityMovementPacket.setHeadYaw( headYaw );
        entityMovementPacket.setTeleported( false );
        entityMovementPacket.setOnGround( this.isOnGround );
        this.getWorld().sendDimensionPacket( entityMovementPacket, this.dimension );
    }

    public void moveEntity( float velocityX, float velocityY, float velocityZ ) {
        if ( velocityX == 0 && velocityY == 0 && velocityZ == 0 ) {
            return;
        }
        this.ySize *= 0.4;

        double movX = velocityX;
        double movY = velocityY;
        double movZ = velocityZ;

        List<AxisAlignedBB> list = this.getWorld().getCollisionCubes( this, this.boundingBox.addCoordinates( velocityX, velocityY, velocityZ ), false );

        for ( AxisAlignedBB bb : list ) {
            velocityY = bb.calculateYOffset( this.boundingBox, velocityY );
        }
        this.boundingBox.offset( 0, velocityY, 0 );

        for ( AxisAlignedBB bb : list ) {
            velocityX = bb.calculateXOffset( this.boundingBox, velocityX );
        }
        this.boundingBox.offset( velocityX, 0, 0 );

        for ( AxisAlignedBB bb : list ) {
            velocityZ = bb.calculateZOffset( this.boundingBox, velocityZ );
        }
        this.boundingBox.offset( 0, 0, velocityZ );

        this.setX( ( this.boundingBox.getMinX() + this.boundingBox.getMaxX() ) / 2 );
        this.setY( this.boundingBox.getMinY() - this.ySize );
        this.setZ( ( this.boundingBox.getMinZ() + this.boundingBox.getMaxZ() ) / 2 );

        this.checkGroundState( movX, movY, movZ, velocityX, velocityY, velocityZ );
        this.updateFallState( velocityY );

        if ( movX != velocityX ) {
            this.velocity.setX( 0 );
        }

        if ( movY != velocityY ) {
            this.velocity.setY( 0 );
        }

        if ( movZ != velocityZ ) {
            this.velocity.setZ( 0 );
        }
    }

    private void checkGroundState( double movX, double movY, double movZ, float velocityX, float velocityY, float velocityZ ) {
        this.isCollidedVertically = movY != velocityY;
        this.isCollidedHorizontally = ( movX != velocityX || movZ != velocityZ );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.isOnGround = ( movY != velocityY && movY < 0 );
    }

    private void updateFallState( float velocityY ) {
        if ( this.isOnGround ) {
            if ( this.fallDistance > 0 ) {
                //this.handleFall();
            }
            this.fallDistance = 0;
        } else if ( velocityY < 0 ) {
            this.fallDistance -= velocityY;
        }
    }
}
