package org.jukeboxmc.math;

import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Location extends Vector {

    private World world = null;
    private float yaw = 0;
    private float pitch = 0;

    public Location( float x, float y, float z ) {
        super( x, y, z );
    }

    public Location( World world, Vector vector ) {
        super( vector.getX(), vector.getY(), vector.getZ() );
        this.world = world;
    }

    public Location( World world, BlockPosition position ) {
        super( position.getX(), position.getY(), position.getZ() );
        this.world = world;
    }

    public Location( World world, float x, float y, float z, float yaw, float pitch ) {
        super( x, y, z );
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld( World world ) {
        this.world = world;
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
        return this.world.getChunk( this.getFloorX() >> 4, this.getFloorZ() >> 4);
    }

    @Override
    public String toString() {
        return "Location{" +
                "world=" + this.getWorld().getName() +
                ", x=" + this.getX() +
                ", y=" + this.getY() +
                ", z=" + this.getZ() +
                ", yaw=" + this.getY() +
                ", pitch=" + this.getPitch() +
                '}';
    }
}
