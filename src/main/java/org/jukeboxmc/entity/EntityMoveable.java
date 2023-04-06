package org.jukeboxmc.entity;

import org.apache.commons.math3.util.FastMath;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.List;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class EntityMoveable extends Entity {

    protected float ySize = 0;
    protected float gravity = 0.08f;
    protected float drag = 0.02f;
    protected boolean isCollidedVertically;
    protected boolean isCollidedHorizontally;
    protected boolean isCollided;
    protected int fallDistance;

    protected void checkObstruction( float x, float y, float z ) {
        if ( this.location.getWorld().getCollisionCubes( this, this.getBoundingBox(), false ).size() == 0 ) {
            return;
        }

        int i = (int) FastMath.floor( x );
        int j = (int) FastMath.floor( y );
        int k = (int) FastMath.floor( z );

        float diffX = x - i;
        float diffY = y - j;
        float diffZ = z - k;

        if ( !this.getWorld().getBlock( i, j, k, 0, this.location.getDimension() ).isTransparent() ) {
            boolean flag = this.getWorld().getBlock( i - 1, j, k, 0, this.location.getDimension() ).isTransparent();
            boolean flag1 = this.getWorld().getBlock( i + 1, j, k, 0, this.location.getDimension() ).isTransparent();
            boolean flag2 = this.getWorld().getBlock( i, j - 1, k, 0, this.location.getDimension() ).isTransparent();
            boolean flag3 = this.getWorld().getBlock( i, j + 1, k, 0, this.location.getDimension() ).isTransparent();
            boolean flag4 = this.getWorld().getBlock( i, j, k - 1, 0, this.location.getDimension() ).isTransparent();
            boolean flag5 = this.getWorld().getBlock( i, j, k + 1, 0, this.location.getDimension() ).isTransparent();

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

            Chunk fromChunk = this.lastLocation.getChunk();
            Chunk toChunk = this.location.getChunk();
            if ( fromChunk.getX() != toChunk.getX() || fromChunk.getZ() != toChunk.getZ() ) {
                fromChunk.removeEntity( this );
                toChunk.addEntity( this );
            }

            this.checkGroundState( movX, movY, movZ, velocity.getX(), velocity.getY(), velocity.getZ() );
            this.updateFallState( movY );

            if ( movX != velocity.getX() ) {
                velocity.setX( 0 );
            }

            if ( movY != velocity.getY() ) {
                velocity.setY( 0 );
            }

            if ( movZ != velocity.getZ() ) {
                velocity.setZ( 0 );
            }

        } else {
            this.boundingBox.offset( movX, movY, movZ );
        }
    }

    protected void updateFallState( float movY ) {
        if ( this.onGround ) {
            if ( this.fallDistance > 0 ) {
                this.fall();
            }
            this.fallDistance = 0;
        } else if ( movY < 0 ) {
            this.fallDistance -= movY;
        }
    }

    protected void checkGroundState( float movX, float movY, float movZ, float dx, float dy, float dz ) {
        this.isCollidedVertically = movY != dy;
        this.isCollidedHorizontally = ( movX != dx || movZ != dz );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.onGround = ( movY != dy && movY < 0 );
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
            this.sendEntityMovePacket( new Location( this.location.getWorld(), this.location.getX(), this.location.getY() + 0, this.location.getZ(), this.location.getYaw(), this.location.getPitch(), this.location.getDimension() ), this.onGround );
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
        moveEntityAbsolutePacket.setRotation( Vector3f.from( location.getPitch(), location.getYaw(), location.getYaw() ));
        Server.getInstance().broadcastPacket( moveEntityAbsolutePacket );
    }

    public void onCollideWithPlayer( Player player ) {
    }

    public float getySize() {
        return this.ySize;
    }

    public float getGravity() {
        return this.gravity;
    }

    public float getDrag() {
        return this.drag;
    }

    public boolean isCollidedVertically() {
        return this.isCollidedVertically;
    }

    public boolean isCollidedHorizontally() {
        return this.isCollidedHorizontally;
    }

    public boolean isCollided() {
        return this.isCollided;
    }

}
