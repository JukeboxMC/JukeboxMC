package org.jukeboxmc.entity;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.packet.*;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockLadder;
import org.jukeboxmc.block.BlockVine;
import org.jukeboxmc.block.BlockWater;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.event.entity.*;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.nukkitx.protocol.bedrock.data.entity.EntityData.BOUNDING_BOX_HEIGHT;
import static com.nukkitx.protocol.bedrock.data.entity.EntityData.BOUNDING_BOX_WIDTH;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    protected long entityId;

    protected Metadata metadata;
    protected Dimension dimension;
    protected Location location;
    protected Location lastLocation;
    protected Vector velocity;
    protected Vector lastVector;
    protected final AxisAlignedBB boundingBox;

    protected long age = 0;
    protected float ySize = 0;
    protected float highestPosition = 0;
    protected float fallDistance = 0;
    protected float gravity = 0.08f;
    protected float drag = 0.02f;

    protected boolean isCollidedVertically;
    protected boolean isCollidedHorizontally;
    protected boolean isCollided;
    protected boolean closed = false;
    protected boolean onGround = false;
    protected boolean isDead = false;

    private final Set<Long> spawnedFor = new HashSet<>();

    public Entity() {
        this.entityId = Entity.entityCount++;
        this.metadata = new Metadata();
        this.metadata.setLong( EntityData.PLAYER_INDEX, 0 );
        this.metadata.setShort( EntityData.MAX_AIR_SUPPLY, (short) 400 );
        this.metadata.setFloat( EntityData.SCALE, 1 );
        this.metadata.setFloat( EntityData.BOUNDING_BOX_WIDTH, this.getWidth() );
        this.metadata.setFloat( EntityData.BOUNDING_BOX_HEIGHT, this.getHeight() );
        this.metadata.setShort( EntityData.AIR_SUPPLY, (short) -1 );
        this.metadata.setFlag( EntityFlag.HAS_GRAVITY, true );
        this.metadata.setFlag( EntityFlag.HAS_COLLISION, true );
        this.metadata.setFlag( EntityFlag.CAN_CLIMB, true );

        this.dimension = Dimension.OVERWORLD;
        this.location = Server.getInstance().getDefaultWorld().getSpawnLocation();
        this.lastLocation = Server.getInstance().getDefaultWorld().getSpawnLocation();
        this.velocity = new Vector( 0, 0, 0, this.dimension );
        this.lastVector = new Vector( 0, 0, 0, this.dimension );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalculateBoundingBox();
    }

    public void update( long currentTick ) {
        this.age++;
    }

    public abstract String getName();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract EntityType getEntityType();

    public boolean canPassThrough() {
        return false;
    }

    protected void fall() {

    }

    public void onCollideWithPlayer( Player player ) {
    }

    public BedrockPacket createSpawnPacket() {
        AddEntityPacket addEntityPacket = new AddEntityPacket();
        addEntityPacket.setRuntimeEntityId( this.entityId );
        addEntityPacket.setUniqueEntityId( this.entityId );
        addEntityPacket.setIdentifier( this.getEntityType().getIdentifier() );
        addEntityPacket.setPosition( this.location.add( 0, this.getEyeHeight(), 0 ).toVector3f() );
        addEntityPacket.setMotion( this.velocity.toVector3f() );
        addEntityPacket.setRotation( Vector3f.from( this.location.getPitch(), this.location.getYaw(), this.location.getYaw() ) );
        addEntityPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        return addEntityPacket;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Location getLocation() {
        return this.location;
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
            this.getWorld().sendDimensionPacket( entityVelocityPacket, velocity.getDimension() );
        }
    }

    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
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
        return this.location.getWorld().getChunk( this.location.getBlockX() >> 4, this.location.getBlockZ() >> 4, this.dimension );
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

        this.metadata.setFloat( BOUNDING_BOX_HEIGHT, this.getHeight() );
        this.metadata.setFloat( BOUNDING_BOX_WIDTH, this.getWidth() );
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

    public Vector getDirectionVector() {
        float pitch = (float) ( ( ( this.getPitch() + 90 ) * Math.PI ) / 180 );
        float yaw = (float) ( ( ( this.getYaw() + 90 ) * Math.PI ) / 180 );
        float x = (float) ( Math.sin( pitch ) * Math.cos( yaw ) );
        float z = (float) ( Math.sin( pitch ) * Math.sin( yaw ) );
        float y = (float) Math.cos( pitch );
        return new Vector( x, y, z ).normalize();
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround( boolean onGround ) {
        this.onGround = onGround;
    }

    public float getEyeHeight() {
        return this.getHeight() / 2 + 0.1f;
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
            player.sendPacket( entity.createSpawnPacket() );
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
            player.sendPacket( removeEntityPacket );
            this.spawnedFor.remove( player.getEntityId() );
        }
        return this;
    }

    public void despawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.despawn( player );
        }
    }

    public void close() {
        this.despawn();
        this.getChunk().removeEntity( this );
        this.getWorld().removeEntity( this );
        this.closed = true;
        this.isDead = true;
    }

    public boolean canCollideWith( Entity entity ) {
        return entity != null && this != entity;
    }

    //============ Metadata =============
    public short getMaxAirSupply() {
        return this.metadata.getShort( EntityData.AIR_SUPPLY );
    }

    public void setMaxAirSupply( short value ) {
        if ( value != this.getMaxAirSupply() ) {
            this.updateMetadata( this.metadata.setShort( EntityData.AIR_SUPPLY, value ) );
        }
    }

    public float getScale() {
        return this.metadata.getFloat( EntityData.SCALE );
    }

    public void setScale( float value ) {
        if ( value != this.getScale() ) {
            this.updateMetadata( this.metadata.setFloat( EntityData.SCALE, value ) );
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
        return this.metadata.getString( EntityData.NAMETAG );
    }

    public void setNameTag( String value ) {
        this.updateMetadata( this.metadata.setString( EntityData.NAMETAG, value ) );
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

        if ( !this.getWorld().getBlock( i, j, k, 0, this.dimension ).isTransparent() ) {
            boolean flag = this.getWorld().getBlock( i - 1, j, k, 0, this.dimension ).isTransparent();
            boolean flag1 = this.getWorld().getBlock( i + 1, j, k, 0, this.dimension ).isTransparent();
            boolean flag2 = this.getWorld().getBlock( i, j - 1, k, 0, this.dimension ).isTransparent();
            boolean flag3 = this.getWorld().getBlock( i, j + 1, k, 0, this.dimension ).isTransparent();
            boolean flag4 = this.getWorld().getBlock( i, j, k - 1, 0, this.dimension ).isTransparent();
            boolean flag5 = this.getWorld().getBlock( i, j, k + 1, 0, this.dimension ).isTransparent();

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
                this.velocity = this.velocity.subtract( force, 0, 0 );
                return;
            }

            if ( direction == 1 ) {
                this.velocity.setX( force );
                return;
            }

            if ( direction == 2 ) {
                this.velocity = this.velocity.subtract( 0, force, 0 );
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

    protected void move( Vector velocity ) {
        if ( velocity.getX() == 0 && velocity.getY() == 0 && velocity.getZ() == 0 ) {
            return;
        }

        this.ySize *= 0.4;

        float movX = velocity.getX();
        float movY = velocity.getY();
        float movZ = velocity.getZ();

        List<AxisAlignedBB> list = this.getWorld().getCollisionCubes( this, this.boundingBox.addCoordinates( velocity.getX(), velocity.getY(), velocity.getZ() ), false );
        if ( list != null ) {

            for ( AxisAlignedBB bb : list ) {
                movY = bb.calculateYOffset( this.boundingBox, movY );
            }
            this.boundingBox.offset( 0, movY, 0 );

            for ( AxisAlignedBB bb : list ) {
                movX = bb.calculateXOffset( this.boundingBox, movX );
            }
            this.boundingBox.offset( movX, 0, 0 );

            for ( AxisAlignedBB bb : list ) {
                movZ = bb.calculateZOffset( this.boundingBox, movZ );
            }
            this.boundingBox.offset( 0, 0, movZ );

            if ( movX != 0 || movY != 0 || movZ != 0 ) {
                this.location.setX( ( this.boundingBox.getMinX() + this.boundingBox.getMaxX() ) / 2 );
                this.location.setY( this.boundingBox.getMinY() - this.ySize );
                this.location.setZ( ( this.boundingBox.getMinZ() + this.boundingBox.getMaxZ() ) / 2 );
            }

            this.checkGroundState( movX, movY, movZ, velocity.getX(), velocity.getY(), velocity.getZ() );
            this.updateFallState( movY );

            if ( movX != velocity.getX() ) {
                this.velocity.setX( 0 );
            }

            if ( movY != velocity.getY() ) {
                this.velocity.setY( 0 );
            }

            if ( movZ != velocity.getZ() ) {
                this.velocity.setZ( 0 );
            }

        } else {
            this.boundingBox.offset( movX, movY, movZ );
        }
    }

    protected void updateFallState( float dY ) {
        if ( this.onGround ) {
            if ( this.fallDistance > 0 ) {
                this.fall();
            }
            this.fallDistance = 0;
        } else if ( dY < 0 ) {
            this.fallDistance -= dY;
        }
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    public void setFallDistance( float fallDistance ) {
        this.fallDistance = fallDistance;
    }

    protected void checkGroundState( float movX, float movY, float movZ, float dx, float dy, float dz ) {
        this.isCollidedVertically = movY != dy;
        this.isCollidedHorizontally = ( movX != dx || movZ != dz );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.onGround = ( movY != dy && movY < 0 );
    }

    public boolean isCollided() {
        return this.isCollided;
    }

    public float getDrag() {
        return this.drag;
    }

    public void setDrag( float drag ) {
        this.drag = drag;
    }

    public float getGravity() {
        return this.gravity;
    }

    public void setGravity( float gravity ) {
        this.gravity = gravity;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void setDead( boolean dead ) {
        isDead = dead;
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
            this.sendEntityMovePacket( new Location( this.location.getWorld(), this.location.getX(), this.location.getY() + 0, this.location.getZ(), this.location.getYaw(), this.getPitch(), this.dimension ), this.onGround );
        }

        if ( diffMotion > 0.0025 || ( diffMotion > 0.0001 && this.getVelocity().squaredLength() <= 0.0001 ) ) {
            this.lastVector.setX( this.velocity.getX() );
            this.lastVector.setY( this.velocity.getY() );
            this.lastVector.setZ( this.velocity.getZ() );
            this.setVelocity( this.velocity, true );
        }
    }

    private void sendEntityMovePacket( Location location, boolean onGround ) {
        MoveEntityAbsolutePacket moveEntityAbsolutePacket = new MoveEntityAbsolutePacket();
        moveEntityAbsolutePacket.setRuntimeEntityId( this.entityId );
        moveEntityAbsolutePacket.setTeleported( false );
        moveEntityAbsolutePacket.setOnGround( onGround );
        moveEntityAbsolutePacket.setPosition( location.toVector3f() );
        moveEntityAbsolutePacket.setRotation( Vector3f.from( location.getPitch(), location.getYaw(), location.getYaw() ) );
        this.getWorld().sendDimensionPacket( moveEntityAbsolutePacket, this.dimension );
    }

    protected boolean isOnLadder() {
        Location location = this.getLocation();
        Chunk loadedChunk = location.getWorld().getLoadedChunk( location.getChunkX(), location.getChunkZ(), location.getDimension() );
        if ( loadedChunk == null ) {
            return false;
        }
        Block block = loadedChunk.getBlock( location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0 );
        return block instanceof BlockLadder || block instanceof BlockVine;
    }

    public boolean isInWater() {
        Vector eyeLocation = this.getLocation().add( 0, this.getEyeHeight(), 0 );
        Chunk loadedChunk = this.getWorld().getLoadedChunk( eyeLocation.getChunkX(), eyeLocation.getChunkZ(), eyeLocation.getDimension() );
        if ( loadedChunk == null ) {
            return false;
        }


        Block block = loadedChunk.getBlock( eyeLocation.getBlockX(), eyeLocation.getBlockY(), eyeLocation.getBlockZ(), 0 );
        if ( block instanceof BlockWater ) {
            float yLiquid = (float) ( ( block.getLocation().getY() + 1 + ( (BlockWater) block ).getLiquidDepth() - 0.12 ) );
            return eyeLocation.getY() < yLiquid;
        }

        return false;
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
