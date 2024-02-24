package org.jukeboxmc.api.event.inventory

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.player.Player

class InventoryOpenEvent(
    inventory: Inventory,
    private val player: Player
) : InventoryEvent(inventory), Cancellable {

    fun getPlayer(): Player {
        return this.player
    }
}