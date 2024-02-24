package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.player.Player

class PlayerQuitEvent(
    player: Player,
    private var quitMessage: String? = null
) : PlayerEvent(player) {

    fun getQuitMessage(): String? {
        return this.quitMessage
    }

    fun setQuitMessage(quitMessage: String?) {
        this.quitMessage = quitMessage
    }

}