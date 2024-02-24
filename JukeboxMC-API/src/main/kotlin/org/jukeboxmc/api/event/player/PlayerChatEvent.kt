package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerChatEvent(
    player: Player,
    private var format: String,
    private var message: String
) : PlayerEvent(player), Cancellable {

    fun getFormat(): String {
        return this.format
    }

    fun setFormat(format: String) {
        this.format = format
    }

    fun getMessage(): String {
        return this.message
    }

    fun setMessage(message: String) {
        this.message = message
    }

}