package org.jukeboxmc.item;

import org.jukeboxmc.entity.projectile.EntitySnowball;
import org.jukeboxmc.event.entity.ProjectileLaunchEvent;
import org.jukeboxmc.item.behavior.ItemProjectile;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSnowball extends ItemProjectile {

    public ItemSnowball() {
        super ( "minecraft:snowball" );
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }

    @Override
    public EntitySnowball getEntity() {
        return new EntitySnowball();
    }

    @Override
    public float getThrowForce() {
        return 1.5f;
    }

    @Override
    public ProjectileLaunchEvent.Cause getCause() {
        return ProjectileLaunchEvent.Cause.SNOWBALL;
    }
}
