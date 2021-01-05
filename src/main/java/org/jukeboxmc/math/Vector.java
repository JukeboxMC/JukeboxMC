package org.jukeboxmc.math;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Vector {

    public static final Vector UP = new Vector( 0, 1, 0 );
    public static final Vector DOWN = new Vector( 0, -1, 0 );

    public static final Vector NORTH = new Vector( 0, 0, -1 );
    public static final Vector EAST = new Vector( 1, 0, 0 );
    public static final Vector SOUTH = new Vector( 0, 0, 1 );
    public static final Vector WEST = new Vector( -1, 0, 0 );

    private float x;
    private float y;
    private float z;

    public Vector( float x, float y, float z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public int getFloorX() {
        return (int) Math.floor( this.x );
    }

    public void setX( float x ) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public int getFloorY() {
        return (int) Math.floor( this.y );
    }

    public void setY( float y ) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public int getFloorZ() {
        return (int) Math.floor( this.z );
    }

    public void setZ( float z ) {
        this.z = z;
    }

    public Vector add( float x, float y, float z ) {
        return new Vector( this.x + x, this.y + y, this.z + z );
    }

    public Vector subtract( float x, float y, float z ) {
        return new Vector( this.x - x, this.y - y, this.z - z );
    }

    public BlockPosition toBlockPosition() {
        return new BlockPosition( this.getFloorX(), this.getFloorY(), this.getFloorZ() );
    }
}
