package org.jukeboxmc.entity.projectile;

import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.item.ItemEgg;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.world.Particle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityEgg extends EntityProjectile {

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
            this.spawnEggParticle( this.location );
            this.closeDelay = true;
        }

        super.update( currentTick );
    }

    @Override
    public String getName() {
        return "Egg";
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
        return EntityType.EGG;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    private void spawnEggParticle( Location location ) {
        ItemEgg itemEgg = new ItemEgg();
        for ( int i = 0; i < 6; i++ ) {
            this.getWorld().spawnParticle( Particle.ITEM_BREAK, location.add( 0f, 0.5f, 0f ), (itemEgg.getRuntimeId() << 16 | itemEgg.getMeta()) );
        }
    }
}
