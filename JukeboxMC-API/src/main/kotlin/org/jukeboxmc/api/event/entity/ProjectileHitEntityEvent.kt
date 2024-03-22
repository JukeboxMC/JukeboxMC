package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.projectile.EntityProjectile
import org.jukeboxmc.api.event.Cancellable

class ProjectileHitEntityEvent(
    entity: Entity,
    private val entityProjectile: EntityProjectile
) : EntityEvent(entity), Cancellable {

    fun getProjectile(): EntityProjectile {
        return this.entityProjectile
    }

}