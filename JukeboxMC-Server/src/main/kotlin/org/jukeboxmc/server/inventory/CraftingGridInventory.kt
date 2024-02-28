package org.jukeboxmc.server.inventory

import org.jukeboxmc.api.inventory.InventoryHolder

abstract class CraftingGridInventory(inventoryHolder: InventoryHolder, size: Int) : ContainerInventory(inventoryHolder, size) {

    abstract fun getOffset(): Int

}