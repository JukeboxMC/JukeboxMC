package org.jukeboxmc.api.event.inventory

import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.player.Player

class InventoryCloseEvent(
    inventory: Inventory,
    private val player: Player
) : InventoryEvent(inventory){

    fun getPlayer(): Player {
        return this.player
    }

}