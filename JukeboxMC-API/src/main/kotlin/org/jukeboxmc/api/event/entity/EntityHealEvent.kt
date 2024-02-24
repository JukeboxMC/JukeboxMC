package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Cancellable

class EntityHealEvent(
    entity: Entity,
    private var heal: Int,
    private val cause: Cause
) : EntityEvent(entity), Cancellable {

    fun getHeal(): Int {
        return this.heal
    }

    fun setHeal(value: Int) {
        this.heal = value
    }

    fun getCause(): Cause {
        return this.cause
    }

    enum class Cause {
        SATURATION,
        REGENERATION_EFFECT,
        HEALING_EFFECT
    }
}