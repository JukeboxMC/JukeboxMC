package org.jukeboxmc.entity;

import org.apache.commons.math3.util.FastMath;
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
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    protected long entityId;

    protected float fallDistance = 0;
    protected float gravity = 0.08f;
    protected float drag = 0.02f;
    protected float ySize = 0;

    protected boolean onGround = false;
    protected boolean spawned = false;
    protected boolean closed = false;

    protected boolean isCollidedVertically;
    protected boolean isCollidedHorizontally;
    protected boolean isCollided;

    protected Location location;
    protected Location lastLocation;

    protected Vector velocity;
    protected Vector lastVector;

    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;
    protected Dimension dimension = Dimension.OVERWORLD;

    public Entity() {
        this.entityId = Entity.entityCount++;

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

        World defaultWorld = Server.getInstance().getDefaultWorld();
        this.location = new Location( defaultWorld, 0, 73, 0, 0, 0, this.dimension );
        this.lastLocation = new Location( defaultWorld, 0, 0, 0, 0, 0, this.dimension );
        this.velocity = new Vector( 0, 0, 0, this.dimension );
        this.lastVector = new Vector( 0, 0, 0, this.dimension );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalculateBoundingBox();
    }

    public void update( long currentTick ) {

    }

    // ========== Movement ==========

    public void move( Vector velocity ) {
        if ( velocity.getX() == 0 && velocity.getY() == 0 && velocity.getZ() == 0 ) {
            return;
        }

        this.ySize *= 0.4;

        float movX = velocity.getX();
        float movY = velocity.getY();
        float movZ = velocity.getZ();

        List<AxisAlignedBB> list = this.getWorld().getCollisionCubes( this, this.boundingBox.addCoordinates( velocity.getX(), velocity.getY(), velocity.getZ() ), false );

        for ( AxisAlignedBB bb : list ) {
            velocity.setY( bb.calculateYOffset( this.boundingBox, velocity.getY() ) );
        }
        this.boundingBox.offset( 0, velocity.getY(), 0 );

        for ( AxisAlignedBB bb : list ) {
            velocity.setX( bb.calculateXOffset( this.boundingBox, velocity.getX() ) );
        }
        this.boundingBox.offset( velocity.getX(), 0, 0 );

        for ( AxisAlignedBB bb : list ) {
            velocity.setZ( bb.calculateZOffset( this.boundingBox, velocity.getZ() ) );
        }
        this.boundingBox.offset( 0, 0, velocity.getZ() );

        this.location.setX( ( this.boundingBox.getMinX() + this.boundingBox.getMaxX() ) / 2 );
        this.location.setY( this.boundingBox.getMinY() - this.ySize );
        this.location.setZ( ( this.boundingBox.getMinZ() + this.boundingBox.getMaxZ() ) / 2 );

        this.checkGroundState( movX, movY, movZ, velocity.getX(), velocity.getY(), velocity.getZ() );
        this.updateFallState( velocity.getY() );

        if ( movX != velocity.getX() ) {
            this.velocity.setX( 0 );
        }

        if ( movY != velocity.getY() ) {
            this.velocity.setY( 0 );
        }

        if ( movZ != velocity.getZ() ) {
            this.velocity.setZ( 0 );
        }
    }

    protected void updateMovement() {
        float diffPosition = ( this.location.getX() - this.lastLocation.getX() ) * ( this.location.getX() - this.lastLocation.getX() ) + ( this.location.getY() - this.lastLocation.getY() ) * ( this.location.getY() - this.lastLocation.getY() ) + ( this.location.getZ() - this.lastLocation.getZ() ) * ( this.location.getZ() - this.lastLocation.getZ() );
        float diffRotation = ( this.location.getYaw() - this.lastLocation.getYaw() ) * ( this.location.getYaw() - this.lastLocation.getYaw() ) + ( this.location.getPitch() - this.lastLocation.getPitch() ) * ( this.location.getPitch() - this.lastLocation.getPitch() );

        float diffMotion = ( this.velocity.getX() - this.lastVector.getX() ) * ( this.velocity.getX() - this.lastVector.getX() ) + ( this.velocity.getY() - this.lastVector.getY() ) * ( this.velocity.getY() - this.lastVector.getY() ) + ( this.velocity.getZ() - this.lastVector.getZ() ) * ( this.velocity.getZ() - this.lastVector.getZ() );

        if ( diffPosition > 0.0001 || diffRotation > 1.0 ) {
            this.lastLocation.setX( this.location.getX() );
            this.lastLocation.setY( this.location.getY() );
            this.lastLocation.setZ( this.location.getZ() );
            this.lastLocation.setYaw( this.location.getYaw() );
            this.lastLocation.setPitch( this.location.getPitch() );
            this.sendEntityMovePacket( new Location( this.location.getWorld(), this.location.getX(), this.location.getY() + 0.125f, this.location.getZ(), this.location.getYaw(), this.getPitch(), this.dimension ), this.onGround );
        }

        if ( diffMotion > 0.0025 || ( diffMotion > 0.0001 && this.getVelocity().squaredLength() <= 0.0001 ) ) {
            this.lastVector.setX( this.velocity.getX() );
            this.lastVector.setY( this.velocity.getY() );
            this.lastVector.setZ( this.velocity.getZ() );
            this.setVelocity( this.velocity, true );
        }
    }


    protected void updateFallState( float dY ) {
        if ( this.onGround ) {
            if ( this.fallDistance > 0 ) {
                //this.handleFall();
            }
            this.fallDistance = 0;
        } else if ( dY < 0 ) {
            this.fallDistance -= dY;
        }
    }

    protected void checkGroundState( float movX, float movY, float movZ, float dx, float dy, float dz ) {
        this.isCollidedVertically = movY != dy;
        this.isCollidedHorizontally = ( movX != dx || movZ != dz );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.onGround = ( movY != dy && movY < 0 );
    }

    protected void checkObstruction( float x, float y, float z ) {
        if ( this.getWorld().getCollisionCubes( this, this.getBoundingBox(), false ).size() == 0 ) {
            return;
        }

        int i = (int) FastMath.floor( x );
        int j = (int) FastMath.floor( y );
        int k = (int) FastMath.floor( z );

        float diffX = x - i;
        float diffY = y - j;
        float diffZ = z - k;

        if ( !this.getWorld().getBlockAt( i, j, k, this.dimension ).isTransparent() ) {
            boolean flag = this.getWorld().getBlockAt( i - 1, j, k, this.dimension  ).isTransparent();
            boolean flag1 = this.getWorld().getBlockAt( i + 1, j, k, this.dimension  ).isTransparent();
            boolean flag2 = this.getWorld().getBlockAt( i, j - 1, k, this.dimension  ).isTransparent();
            boolean flag3 = this.getWorld().getBlockAt( i, j + 1, k, this.dimension  ).isTransparent();
            boolean flag4 = this.getWorld().getBlockAt( i, j, k - 1, this.dimension  ).isTransparent();
            boolean flag5 = this.getWorld().getBlockAt( i, j, k + 1, this.dimension  ).isTransparent();

            int direction = -1;
            double limit = 9999;

            if ( flag ) {
                limit = diffX;
                direction = 0;
            }

            if ( flag1 && 1 - diffX < limit ) {
                limit = 1 - diffX;
                direction = 1;
            }

            if ( flag2 && diffY < limit ) {
                limit = diffY;
                direction = 2;
            }

            if ( flag3 && 1 - diffY < limit ) {
                limit = 1 - diffY;
                direction = 3;
            }

            if ( flag4 && diffZ < limit ) {
                limit = diffZ;
                direction = 4;
            }

            if ( flag5 && 1 - diffZ < limit ) {
                direction = 5;
            }

            float force = new Random().nextFloat() * 0.2f + 0.1f;

            if ( direction == 0 ) {
                this.velocity = this.velocity.subtract( force, 0 ,0 );
                return;
            }

            if ( direction == 1 ) {
                this.velocity.setX( force );
                return;
            }

            if ( direction == 2 ) {
                this.velocity = this.velocity.subtract( 0, force ,0 );
                return;
            }

            if ( direction == 3 ) {
                this.velocity.setY( force );
                return;
            }

            if ( direction == 4 ) {
                this.velocity = this.velocity.subtract( 0, 0, force );
                return;
            }

            if ( direction == 5 ) {
                this.velocity.setZ( force );
            }
        }

    }

    private void sendEntityMovePacket( Location location, boolean onGround ) {
        EntityMovementPacket entityMovementPacket = new EntityMovementPacket();
        entityMovementPacket.setEntityId( this.entityId );
        entityMovementPacket.setTeleported( false );
        entityMovementPacket.setOnGround( onGround );
        entityMovementPacket.setX( location.getX() );
        entityMovementPacket.setY( location.getY() );
        entityMovementPacket.setZ( location.getZ() );
        entityMovementPacket.setYaw( location.getYaw() );
        entityMovementPacket.setHeadYaw( location.getYaw() );
        entityMovementPacket.setPitch( location.getPitch() );
        this.getWorld().sendDimensionPacket( entityMovementPacket, this.dimension );
    }

    public abstract String getName();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract Packet createSpawnPacket();

    public void onCollideWithPlayer( Player player ) {
    }

    public long getEntityId() {
        return this.entityId;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround( boolean onGround ) {
        this.onGround = onGround;
    }

    public boolean isSpawned() {
        return this.spawned;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
        this.recalculateBoundingBox();
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation( Location lastLocation ) {
        this.lastLocation = lastLocation;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public void setWorld( World world ) {
        this.location.setWorld( world );
    }

    public float getX() {
        return this.location.getX();
    }

    public int getBlockX() {
        return this.location.getBlockX();
    }

    public void setX( float x ) {
        this.location.setX( x );
    }

    public float getY() {
        return this.location.getY();
    }

    public int getBlockY() {
        return this.location.getBlockY();
    }

    public void setY( float y ) {
        this.location.setY( y );
    }

    public float getZ() {
        return this.location.getZ();
    }

    public int getBlockZ() {
        return this.location.getBlockZ();
    }

    public void setZ( float z ) {
        this.location.setZ( z );
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
        return this.location.getWorld().getChunk( this.location.getBlockX() >> 4, this.location.getBlockZ() >> 4, this.dimension );
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
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

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
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
            EntityVelocityPacket entityVelocityPacket = new EntityVelocityPacket();
            entityVelocityPacket.setEntityId( entityVelocityEvent.getEntity().getEntityId() );
            entityVelocityPacket.setVelocity( entityVelocityEvent.getVelocity() );
            this.getWorld().sendDimensionPacket( entityVelocityPacket, velocity.getDimension() );
        }
    }

    public Vector getLastVector() {
        return this.lastVector;
    }

    public void setLastVector( Vector lastVector ) {
        this.lastVector = lastVector;
    }

    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
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

    public float getEyeHeight() {
        return this.getHeight() / 2 + 0.1f;
    }

    public Entity spawn( Player player ) {
        EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent( this );
        Server.getInstance().getPluginManager().callEvent( entitySpawnEvent );
        if ( entitySpawnEvent.isCancelled() ) {
            return this;
        }
        Entity entity = entitySpawnEvent.getEntity();
        entity.setLocation( this.location );

        entity.getChunk().addEntity( this );
        entity.getWorld().addEntity( this );

        player.getPlayerConnection().sendPacket( entity.createSpawnPacket() );
        this.spawned = true;
        return this;
    }

    public Entity spawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.spawn( player );

        }
        return this;
    }

    public void despawn( Player player ) {
        RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
        removeEntityPacket.setEntityId( this.entityId );
        player.getPlayerConnection().sendPacket( removeEntityPacket );

        this.getChunk().removeEntity( this );
        this.getWorld().removeEntity( this );
        this.closed = true;
    }

    public void despawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.despawn( player );
        }
    }

    public boolean canPassThrough() {
        return false;
    }

    // ========== Metadata ==========

    public void updateMetadata( Metadata metadata ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.entityId );
        setEntityDataPacket.setMetadata( metadata );
        setEntityDataPacket.setTick( Server.getInstance().getCurrentTick() );
        Server.getInstance().broadcastPacket( setEntityDataPacket );
    }

    public void updateMetadata() {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.entityId );
        setEntityDataPacket.setMetadata( this.metadata );
        setEntityDataPacket.setTick( Server.getInstance().getCurrentTick() );
        Server.getInstance().broadcastPacket( setEntityDataPacket );
    }

    public short getMaxAirSupply() {
        return this.metadata.getShort( MetadataFlag.AIR_SUPPLY );
    }

    public void setMaxAirSupply( short value ) {
        if ( value != this.getMaxAirSupply() ) {
            this.updateMetadata( this.metadata.setShort( MetadataFlag.AIR_SUPPLY, value ) );
        }
    }

    public float getScale() {
        return this.metadata.getFloat( MetadataFlag.SCALE );
    }

    public void setScale( float value ) {
        if ( value != this.getScale() ) {
            this.updateMetadata( this.metadata.setFloat( MetadataFlag.SCALE, value ) );
        }
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
}
