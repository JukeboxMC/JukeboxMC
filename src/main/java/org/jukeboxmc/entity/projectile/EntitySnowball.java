package org.jukeboxmc.entity.projectile;

import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.world.Particle;

public class EntitySnowball extends EntityProjectile {

    private boolean closeDelay = false;

    @Override
    public void update( long currentTick ) {
        if ( this.closed ) {
            return;
        }

        if ( this.closeDelay ) {
            this.close();
            this.closeDelay = false;
        }

        if ( this.age > 1200 || this.isCollided ) {
            this.updateMovement();
            this.displaySnowballPoofParticle( this.location );
            this.closeDelay = true;
        }

        super.update( currentTick );
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
    public EntityType getEntityType() {
        return EntityType.SNOWBALL;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    @Override
    public float getDrag() {
        return 0.01f;
    }

    @Override
    public float getGravity() {
        return 0.03f;
    }

    private void displaySnowballPoofParticle( Location location ) {
        for ( int i = 0; i < 6; i++ ) {
            this.getWorld().spawnParticle( Particle.SNOWBALL_POOF, location.add( 0f, 0.5f, 0f ) );
        }
    }
}
