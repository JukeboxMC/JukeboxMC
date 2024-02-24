package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.player.skin.Skin

class PlayerChangeSkinEvent(
    player: Player,
    private var skin: Skin
) : PlayerEvent(player), Cancellable {

    fun getSkin(): Skin {
        return this.skin
    }

    fun setSkin(skin: Skin) {
        this.skin = skin
    }

}