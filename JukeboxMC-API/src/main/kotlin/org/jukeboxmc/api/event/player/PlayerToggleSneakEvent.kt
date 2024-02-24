package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player

class PlayerToggleSneakEvent(
    player: Player,
    private val sneaking: Boolean
) : PlayerEvent(player), Cancellable {

    fun isSneaking(): Boolean {
        return this.sneaking
    }

}