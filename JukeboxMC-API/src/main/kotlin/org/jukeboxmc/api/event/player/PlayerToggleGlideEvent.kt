package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerToggleGlideEvent(
    player: Player,
    private val gliding: Boolean
) : PlayerEvent(player), Cancellable {

    fun isGliding(): Boolean {
        return this.gliding
    }
}