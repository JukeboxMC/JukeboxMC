package org.jukeboxmc.math;

import lombok.SneakyThrows;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Location extends Vector implements Cloneable {

    private World world;
    private float yaw = 0;
    private float pitch = 0;
    private float headYaw = 0;

    public Location( World world, float x, float y, float z ) {
        super( x, y, z );
        this.world = world;
    }

    public Location( World world, Vector vector ) {
        super( vector.getX(), vector.getY(), vector.getZ(), vector.getDimension() );
        this.world = world;
        this.dimension = vector.getDimension();
    }

    public Location( World world, float x, float y, float z, float yaw, float pitch ) {
        super( x, y, z );
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location( World world, float x, float y, float z, float yaw, float pitch, Dimension dimension ) {
        super( x, y, z );
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
        this.dimension = dimension;
    }

    public Location( World world, float x, float y, float z, float headYaw, float yaw, float pitch ) {
        this( world, x, y, z, yaw, pitch );
        this.headYaw = headYaw;
    }

    public Location( World world, float x, float y, float z, float headYaw, float yaw, float pitch, Dimension dimension ) {
        this( world, x, y, z, yaw, pitch );
        this.headYaw = headYaw;
        this.dimension = dimension;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld( World world ) {
        this.world = world;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw( float headYaw ) {
        this.headYaw = headYaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw( float yaw ) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch( float pitch ) {
        this.pitch = pitch;
    }

    public Chunk getChunk() {
        return this.world.getChunk( this.getFloorX() >> 4, this.getFloorZ() >> 4, this.dimension );
    }

    public Block getBlock() {
        return this.world.getBlock( this );
    }

    public Location divide( float x, float z ) {
        return new Location( this.world, this.x / x, this.getY(), this.z / z );
    }

    @Override
    @SneakyThrows
    public Location clone() {
        Location location = (Location) super.clone();
        location.setWorld( this.getWorld() );
        location.setX( this.getX() );
        location.setY( this.getY() );
        location.setZ( this.getZ() );
        location.setHeadYaw( this.getHeadYaw() );
        location.setYaw( this.getYaw() );
        location.setPitch( this.getPitch() );
        location.setDimension( this.getDimension() );
        return location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "world=" + this.getWorld().getName() +
                ", x=" + this.getX() +
                ", y=" + this.getY() +
                ", z=" + this.getZ() +
                ", headYaw=" + this.getHeadYaw() +
                ", yaw=" + this.getY() +
                ", pitch=" + this.getPitch() +
                '}';
    }
}
