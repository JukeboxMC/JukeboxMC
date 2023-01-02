package org.jukeboxmc.entity.projectile;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Particle;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntitySnowball extends EntityProjectile {

    @Override
    public void update( long currentTick ) {
        if ( this.isClosed() ) {
            return;
        }
        super.update( currentTick );

        if ( this.age > TimeUnit.MINUTES.toMillis( 5 ) || this.isCollided ) {
            this.close();
            this.spawnSnowballParticle( this.location );
        }
    }

    @Override
    public void onCollidedWithEntity( Entity entity ) {
        this.close();
    }

    @Override
    public String getName() {
        return "Snowball";
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    public float getDrag() {
        return 0.01f;
    }

    @Override
    public float getGravity() {
        return 0.03f;
    }

    @Override
    public EntityType getType() {
        return EntityType.SNOWBALL;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:snowball" );
    }

    private void spawnSnowballParticle( Location location ) {
        for ( int i = 0; i < 6; i++ ) {
            this.getWorld().spawnParticle( Particle.SNOWBALL_POOF, location.add( 0f, 0.5f, 0f ) );
        }
    }
}
