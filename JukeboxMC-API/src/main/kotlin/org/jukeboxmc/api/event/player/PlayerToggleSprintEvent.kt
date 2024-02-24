package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerToggleSprintEvent(
    player: Player,
    private val sprinting: Boolean
) : PlayerEvent(player), Cancellable {

    fun isSprinting(): Boolean {
        return this.sprinting
    }

}