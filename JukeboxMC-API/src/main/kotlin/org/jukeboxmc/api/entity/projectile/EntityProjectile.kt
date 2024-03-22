package org.jukeboxmc.api.entity.projectile

import org.jukeboxmc.api.entity.Entity

interface EntityProjectile : Entity {

    fun getShooter(): Entity?

    fun setShooter(entity: Entity?)
}