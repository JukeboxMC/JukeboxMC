package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Cancellable

class EntityRemoveEffectEvent(
        entity: Entity,
        private var effect: Effect
) : EntityEvent(entity), Cancellable {

    fun getEffect(): Effect {
        return this.effect
    }
}