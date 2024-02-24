package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Event
import org.jukeboxmc.api.player.Player

abstract class PlayerEvent(private val player: Player) : Event() {

    fun getPlayer(): Player {
        return this.player
    }
}