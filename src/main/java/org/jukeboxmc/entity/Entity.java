package org.jukeboxmc.entity;

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
import org.jukeboxmc.network.packet.EntityVelocityPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.RemoveEntityPacket;
import org.jukeboxmc.network.packet.SetEntityDataPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    protected long entityId;

    protected boolean onGround = false;

    protected Location location;
    protected Vector velocity;

    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;
    protected Dimension dimension = Dimension.OVERWORLD;

    protected Map<Long, Player> spawnedFor = new ConcurrentHashMap<>();

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
        this.velocity = new Vector( 0, 0, 0, this.dimension );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalculateBoundingBox();
    }

    public void update( long currentTick ) {
    }

    public abstract String getName();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract Packet createSpawnPacket();

    public long getEntityId() {
        return this.entityId;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround( boolean onGround ) {
        this.onGround = onGround;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation( Location location ) {
        this.location = location;
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
        if ( !this.spawnedFor.containsKey( player.getEntityId() ) ) {
            EntitySpawnEvent entitySpawnEvent = new EntitySpawnEvent( this );
            Server.getInstance().getPluginManager().callEvent( entitySpawnEvent );
            if ( entitySpawnEvent.isCancelled() ) {
                return this;
            }
            Entity entity = entitySpawnEvent.getEntity();
            entity.setLocation( this.location );

            entity.getWorld().addEntity( this );

            this.spawnedFor.put( player.getEntityId(), player );
            player.getPlayerConnection().sendPacket( entity.createSpawnPacket() );
        }
        return this;
    }

    public Entity spawn() {
        for ( Entity entity : this.getChunk().getEntities() ) {
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
            this.getWorld().removeEntity( this );
            this.spawnedFor.remove( player.getEntityId() );
            player.getPlayerConnection().sendPacket( removeEntityPacket );
        }
    }

    public void despawn() {
        for ( Player player : this.spawnedFor.values() ) {
            this.despawn( player );
        }
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
