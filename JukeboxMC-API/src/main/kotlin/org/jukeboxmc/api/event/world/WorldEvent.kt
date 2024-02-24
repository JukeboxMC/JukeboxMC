package org.jukeboxmc.api.event.world

import org.jukeboxmc.api.event.Event
import org.jukeboxmc.api.world.World

open class WorldEvent(private val world: World) : Event() {

    fun getWorld(): World {
        return this.world
    }

}