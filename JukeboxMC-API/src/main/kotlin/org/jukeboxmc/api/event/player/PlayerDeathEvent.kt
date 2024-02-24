package org.jukeboxmc.api.event.player

import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class PlayerDeathEvent(
    player: Player,
    private var deathMessage: String?,
    private var deathScreenMessage: String?,
    private var dropInventory: Boolean,
    private val drops: MutableList<Item>
) : PlayerEvent(player) {

    fun getDeathMessage(): String? {
        return this.deathMessage
    }

    fun setDeathMessage(deathMessage: String?) {
        this.deathMessage = deathMessage
    }

    fun getDeathScreenMessage(): String? {
        return this.deathScreenMessage
    }

    fun setDeathScreenMessage(deathScreenMessage: String?) {
        this.deathScreenMessage = deathScreenMessage
    }

    fun dropInventory(): Boolean {
        return this.dropInventory
    }

    fun setDropInventory(dropInventory: Boolean) {
        this.dropInventory
    }

    fun getDrops(): MutableList<Item> {
        return this.drops
    }

}