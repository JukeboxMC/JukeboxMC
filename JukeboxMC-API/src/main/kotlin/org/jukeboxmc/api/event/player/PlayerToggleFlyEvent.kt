package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerToggleFlyEvent(
    player: Player,
    private val flying: Boolean
): PlayerEvent(player), Cancellable {

    fun isFlying(): Boolean {
        return this.flying
    }

}