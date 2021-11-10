package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.projectile.EntityProjectile;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ProjectileLaunchEvent extends EntityEvent implements Cancellable {

    private final Cause cause;

    public ProjectileLaunchEvent( Entity entity, Cause cause ) {
        super( entity );
        this.cause = cause;
    }

    @Override
    public EntityProjectile getEntity() {
        return (EntityProjectile) super.getEntity();
    }

    public Cause getCause() {
        return this.cause;
    }

    public enum Cause {
        BOW,
        CROSSBOW,
        EXP_BOTTLE,
        FISHING_ROD,
        ENDER_PEARL,
        SNOWBALL,
        EGG

    }
}
