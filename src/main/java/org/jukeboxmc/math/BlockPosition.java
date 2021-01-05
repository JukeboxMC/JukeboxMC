package org.jukeboxmc.math;

import lombok.ToString;
import org.jukeboxmc.Server;
import org.jukeboxmc.world.World;

@ToString
public class BlockPosition {
    public static final BlockPosition UP = new BlockPosition( 0, 1, 0 );
    public static final BlockPosition DOWN = new BlockPosition( 0, -1, 0 );

    public static final BlockPosition NORTH = new BlockPosition( 0, 0, -1 );
    public static final BlockPosition EAST = new BlockPosition( 1, 0, 0 );
    public static final BlockPosition SOUTH = new BlockPosition( 0, 0, 1 );
    public static final BlockPosition WEST = new BlockPosition( -1, 0, 0 );

    private World world;
    private int x;
    private int y;
    private int z;

    public BlockPosition(int x, int y, int z) {
        this(x, y, z, Server.getInstance().getDefaultWorld());
    }

    public BlockPosition(Vector vector) {
        this(vector.getFloorX(), vector.getFloorY(), vector.getFloorZ(), Server.getInstance().getDefaultWorld());
    }

    public BlockPosition(int x, int y, int z, World world) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPosition(Vector vector, World world) {
        this.world = world;
        this.x = vector.getFloorX();
        this.y = vector.getFloorY();
        this.z = vector.getFloorZ();
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
