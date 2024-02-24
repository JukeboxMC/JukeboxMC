package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class PlayerConsumeItemEvent(
        player: Player,
        private val item: Item
) : PlayerEvent(player), Cancellable {

    fun getItem(): Item {
        return this.item
    }

}