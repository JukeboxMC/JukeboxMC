package org.jukeboxmc.entity;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.SetEntityDataPacket;
import org.jukeboxmc.network.packet.SpawnParticleEffectPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Particle;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    private long entityId = 0;

    protected Location location;
    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;
    protected Dimension dimension = Dimension.OVERWORLD;

    protected boolean isOnGround = false;

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

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalcBoundingBox();
    }

    public abstract String getName();

    public abstract String getEntityType();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract float getEyeHeight();

    public abstract Packet createSpawnPacket();

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId( long entityId ) {
        this.entityId = entityId;
    }
    
    public Location getLocation() {
        return this.location;
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
        this.recalcBoundingBox();
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
        this.recalcBoundingBox();
    }

    public boolean hasCollision() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION );
    }

    public void setCollision( boolean value ) {
        if(this.hasCollision() != value){
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, value ) );
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

    public void recalcBoundingBox() {
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
}
