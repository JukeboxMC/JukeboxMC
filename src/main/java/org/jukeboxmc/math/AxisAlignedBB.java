package org.jukeboxmc.math;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class AxisAlignedBB {

    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;

    public AxisAlignedBB( float minX, float minY, float minZ, float maxX, float maxY, float maxZ ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public AxisAlignedBB setBounds( float minX, float minY, float minZ, float maxX, float maxY, float maxZ ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    public AxisAlignedBB setBounds( AxisAlignedBB other ) {
        this.minX = other.minX;
        this.minY = other.minY;
        this.minZ = other.minZ;
        this.maxX = other.maxX;
        this.maxY = other.maxY;
        this.maxZ = other.maxZ;
        return this;
    }

    public AxisAlignedBB addCoordinates( float x, float y, float z ) {
        float minX = this.minX;
        float minY = this.minY;
        float minZ = this.minZ;
        float maxX = this.maxX;
        float maxY = this.maxY;
        float maxZ = this.maxZ;

        if ( x < 0 ) {
            minX += x;
        } else if ( x > 0 ) {
            maxX += x;
        }

        if ( y < 0 ) {
            minY += y;
        } else if ( y > 0 ) {
            maxY += y;
        }

        if ( z < 0 ) {
            minZ += z;
        } else if ( z > 0 ) {
            maxZ += z;
        }

        return new AxisAlignedBB( minX, minY, minZ, maxX, maxY, maxZ );
    }

    public AxisAlignedBB grow( float x, float y, float z ) {
        return new AxisAlignedBB( this.minX - x, this.minY - y, this.minZ - z, this.maxX + x, this.maxY + y, this.maxZ + z );
    }

    public AxisAlignedBB expand( float x, float y, float z ) {
        this.minX -= x;
        this.minY -= y;
        this.minZ -= z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public AxisAlignedBB offset( float x, float y, float z ) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public AxisAlignedBB shrink( float x, float y, float z ) {
        return new AxisAlignedBB( this.minX + x, this.minY + y, this.minZ + z, this.maxX - x, this.maxY - y, this.maxZ - z );
    }

    public AxisAlignedBB contract( float x, float y, float z ) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX -= x;
        this.maxY -= y;
        this.maxZ -= z;
        return this;
    }

    public AxisAlignedBB getOffsetBoundingBox( float x, float y, float z ) {
        return new AxisAlignedBB( this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z );
    }

    public float calculateXOffset( AxisAlignedBB bb, float x ) {
        if ( bb.maxY <= this.minY || bb.minY >= this.maxY ) {
            return x;
        }

        if ( bb.maxZ <= this.minZ || bb.minZ >= this.maxZ ) {
            return x;
        }

        if ( x > 0 && bb.maxX <= this.minX ) {
            float x1 = this.minX - bb.maxX;
            if ( x1 < x ) {
                x = x1;
            }
        }

        if ( x < 0 && bb.minX >= this.maxX ) {
            float x2 = this.maxX - bb.minX;
            if ( x2 > x ) {
                x = x2;
            }
        }

        return x;
    }

    public float calculateYOffset( AxisAlignedBB bb, float y ) {
        if ( bb.maxX <= this.minX || bb.minX >= this.maxX ) {
            return y;
        }

        if ( bb.maxZ <= this.minZ || bb.minZ >= this.maxZ ) {
            return y;
        }

        if ( y > 0 && bb.maxY <= this.minY ) {
            float y1 = this.minY - bb.maxY;
            if ( y1 < y ) {
                y = y1;
            }
        }

        if ( y < 0 && bb.minY >= this.maxY ) {
            float y2 = this.maxY - bb.minY;
            if ( y2 > y ) {
                y = y2;
            }
        }

        return y;
    }

    public float calculateZOffset( AxisAlignedBB bb, float z ) {
        if ( bb.maxX <= this.minX || bb.minX >= this.maxX ) {
            return z;
        }

        if ( bb.maxY <= this.minY || bb.minY >= this.maxY ) {
            return z;
        }

        if ( z > 0 && bb.maxZ <= this.minZ ) {
            float z1 = this.minZ - bb.maxZ;
            if ( z1 < z ) {
                z = z1;
            }
        }

        if ( z < 0 && bb.minZ >= this.maxZ ) {
            float z2 = this.maxZ - bb.minZ;
            if ( z2 > z ) {
                z = z2;
            }
        }

        return z;
    }

    public boolean intersectsWith( AxisAlignedBB bb ) {
        if ( bb.maxX - this.minX > 0.01f && this.maxX - bb.minX > 0.01f ) {
            if ( bb.maxY - this.minY > 0.01f && this.maxY - bb.minY > 0.01f ) {
                return bb.maxZ - this.minZ > 0.01f && this.maxZ - bb.minZ > 0.01f;
            }
        }

        return false;
    }

    public boolean isVectorInside( Vector vector ) {
        return !( vector.getX() <= this.minX || vector.getX() >= this.maxX ) &&
                !( vector.getY() <= this.minY || vector.getY() >= this.maxY ) &&
                ( vector.getZ() > this.minZ || vector.getZ() < this.maxZ );
    }

    public float getAverageEdgeLength() {
        return ( this.maxX - this.minX + this.maxY - this.minY + this.maxZ - this.minZ ) / 3;
    }

    public boolean isVectorInYZ( Vector vector ) {
        return vector.getY() >= this.minY && vector.getY() <= this.maxY && vector.getZ() >= this.minZ && vector.getZ() <= this.maxZ;
    }

    public boolean isVectorInXZ( Vector vector ) {
        return vector.getX() >= this.minX && vector.getX() <= this.maxX && vector.getZ() >= this.minZ && vector.getZ() <= this.maxZ;
    }

    public boolean isVectorInXY( Vector vector ) {
        return vector.getX() >= this.minX && vector.getX() <= this.maxX && vector.getY() >= this.minY && vector.getY() <= this.maxY;
    }

    public Vector calculateIntercept( Vector pos1, Vector pos2 ) {
        Vector v1 = pos1.getVectorWhenXIsOnLine( pos2, this.minX );
        Vector v2 = pos1.getVectorWhenXIsOnLine( pos2, this.maxX );
        Vector v3 = pos1.getVectorWhenYIsOnLine( pos2, this.minY );
        Vector v4 = pos1.getVectorWhenYIsOnLine( pos2, this.maxY );
        Vector v5 = pos1.getVectorWhenZIsOnLine( pos2, this.minZ );
        Vector v6 = pos1.getVectorWhenZIsOnLine( pos2, this.maxZ );

        Vector resultVector = null;
        if ( v1 != null && this.isVectorInYZ( v1 ) ) {
            resultVector = v1;
        }

        if ( v2 != null && this.isVectorInYZ( v2 ) &&
                ( resultVector == null || pos1.distanceSquared( v2 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v2;
        }

        if ( v3 != null && this.isVectorInXZ( v3 ) &&
                ( resultVector == null || pos1.distanceSquared( v3 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v3;
        }

        if ( v4 != null && this.isVectorInXZ( v4 ) &&
                ( resultVector == null || pos1.distanceSquared( v4 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v4;
        }

        if ( v5 != null && this.isVectorInXY( v5 ) &&
                ( resultVector == null || pos1.distanceSquared( v5 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v5;
        }

        if ( v6 != null && this.isVectorInXY( v6 ) &&
                ( resultVector == null || pos1.distanceSquared( v6 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v6;
        }

        return resultVector;
    }

    @Override
    public AxisAlignedBB clone() {
        try {
            AxisAlignedBB clone = (AxisAlignedBB) super.clone();
            return clone.setBounds( this );
        } catch ( CloneNotSupportedException e ) {
            return new AxisAlignedBB( this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ );
        }
    }

    public float getMinX() {
        return this.minX;
    }

    public float getMinY() {
        return this.minY;
    }

    public float getMinZ() {
        return this.minZ;
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getMaxZ() {
        return this.maxZ;
    }

}
