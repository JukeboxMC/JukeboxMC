package org.jukeboxmc.api.event.inventory

import org.jukeboxmc.api.event.Event
import org.jukeboxmc.api.inventory.Inventory

open class InventoryEvent(private val inventory: Inventory) : Event() {

    fun getInventory(): Inventory {
        return this.inventory
    }
}