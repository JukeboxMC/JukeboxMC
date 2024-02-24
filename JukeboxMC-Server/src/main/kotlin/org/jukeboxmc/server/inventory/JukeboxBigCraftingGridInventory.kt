package org.jukeboxmc.server.inventory

import org.jukeboxmc.api.inventory.BigCraftingGridInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBigCraftingGridInventory(inventoryHolder: InventoryHolder) : CraftingGridInventory(inventoryHolder, 9), BigCraftingGridInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.BIG_CRAFTING_GRID
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 32)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 32, item, sendContents)
    }
}