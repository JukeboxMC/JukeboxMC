package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity

class EntityDamageByEntityEvent(
    entity: Entity,
    private val damager: Entity?,
    damage: Float,
    damageSource: DamageSource,
) : EntityDamageEvent(entity, damage, damageSource) {

    fun getDamager(): Entity? {
        return this.damager
    }
}