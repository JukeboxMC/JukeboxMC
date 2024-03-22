package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Event

open class EntityEvent(
    private val entity: Entity
) : Event() {

    open fun getEntity(): Entity {
        return this.entity
    }
}