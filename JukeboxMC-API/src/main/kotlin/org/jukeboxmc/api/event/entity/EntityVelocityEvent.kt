package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.math.Vector

class EntityVelocityEvent(
    entity: Entity,
    private var velocity: Vector
) : EntityEvent(entity), Cancellable {

    fun getVelocity(): Vector {
        return this.velocity
    }

    fun setVelocity(velocity: Vector) {
        this.velocity = velocity
    }

}