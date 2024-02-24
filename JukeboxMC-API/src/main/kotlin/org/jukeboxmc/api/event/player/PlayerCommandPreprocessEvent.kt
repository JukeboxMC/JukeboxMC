package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerCommandPreprocessEvent(
        player: Player,
        private val command: String
) : PlayerEvent(player), Cancellable {

    fun getCommand(): String {
        return this.command
    }

}