package org.jukeboxmc.server.inventory

import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.SmallCraftingGridInventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxSmallCraftingGridInventory(inventoryHolder: InventoryHolder) : CraftingGridInventory(inventoryHolder, 4), SmallCraftingGridInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.SMALL_CRAFTING_GRID
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - this.getOffset())
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - this.getOffset(), item, sendContents)
    }

    override fun getOffset(): Int {
        return 28
    }
}