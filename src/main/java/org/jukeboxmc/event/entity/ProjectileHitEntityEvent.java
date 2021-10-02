package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.projectile.EntityProjectile;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ProjectileHitEntityEvent extends EntityEvent implements Cancellable {

    private EntityProjectile projectile;

    public ProjectileHitEntityEvent( Entity entity, EntityProjectile projectile ) {
        super( entity );
        this.projectile = projectile;
    }

    public EntityProjectile getProjectile() {
        return this.projectile;
    }
}
