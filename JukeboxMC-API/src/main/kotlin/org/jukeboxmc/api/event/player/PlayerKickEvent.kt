package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerKickEvent(
        player: Player,
        private var reason: String
) : PlayerEvent(player), Cancellable {

    fun getReason(): String {
        return this.reason
    }

    fun setReason(reason: String) {
        this.reason = reason
    }

}