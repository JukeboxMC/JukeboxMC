package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.projectile.EntityProjectile
import org.jukeboxmc.api.event.Cancellable

class ProjectileLaunchEvent(
    private val entity: EntityProjectile,
    private val cause: Cause
) : EntityEvent(entity), Cancellable {

    fun getCause(): Cause {
        return this.cause
    }

    override fun getEntity(): EntityProjectile {
        return this.entity
    }

    enum class Cause {
        BOW,
        CROSSBOW,
        EXP_BOTTLE,
        FISHING_ROD,
        ENDER_PEARL,
        SNOWBALL,
        EGG
    }

}