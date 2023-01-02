package org.jukeboxmc.entity.projectile;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Particle;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityEgg extends EntityProjectile {

    @Override
    public void update( long currentTick ) {
        if ( this.isClosed() ) {
            return;
        }
        super.update( currentTick );

        if ( this.age > TimeUnit.MINUTES.toMillis( 5 ) || this.isCollided ) {
            this.close();
            this.spawnEggParticle( this.location );
        }
    }

    @Override
    public void onCollidedWithEntity( Entity entity ) {
        this.close();
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
    public EntityType getType() {
        return EntityType.EGG;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:egg" );
    }

    private void spawnEggParticle( Location location ) {
        Item itemEgg = Item.create( ItemType.EGG );
        for ( int i = 0; i < 6; i++ ) {
            this.getWorld().spawnParticle( Particle.ITEM_BREAK, location.add( 0f, 0.5f, 0f ), ( itemEgg.getRuntimeId() << 16 | itemEgg.getMeta() ) );
        }
    }
}
