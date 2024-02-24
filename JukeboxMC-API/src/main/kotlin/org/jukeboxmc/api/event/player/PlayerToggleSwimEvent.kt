package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerToggleSwimEvent(
    player: Player,
    private val swimming: Boolean
) : PlayerEvent(player), Cancellable {

    fun isSwimming(): Boolean {
        return this.swimming
    }

}