package org.jukeboxmc.api.event.inventory

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.player.Player

class InventoryClickEvent(
    private val inventory: Inventory,
    private val destinationInventory: Inventory,
    private val player: Player,
    private val sourceItem: Item,
    private val targetItem: Item,
    private val slot: Int
) : InventoryEvent(inventory), Cancellable {

    fun getDestinationInventory(): Inventory {
        return this.destinationInventory
    }

    fun getPlayer(): Player {
        return this.player
    }

    fun getSourceItem(): Item {
        return this.sourceItem
    }

    fun getTargetItem(): Item {
        return this.targetItem
    }

    fun getSlot(): Int {
        return this.slot
    }

}