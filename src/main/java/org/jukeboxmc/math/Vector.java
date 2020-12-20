package org.jukeboxmc.math;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Vector {

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

}
