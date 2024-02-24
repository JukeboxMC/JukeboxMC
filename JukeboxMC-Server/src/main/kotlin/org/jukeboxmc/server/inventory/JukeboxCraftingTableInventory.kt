package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.CraftingTableInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxCraftingTableInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 9), CraftingTableInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.CRAFTING_TABLE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.WORKBENCH
    }

    override fun onOpen(player: JukeboxPlayer) {
        super.onOpen(player)
        player.setCraftingGridInventory(JukeboxBigCraftingGridInventory(player))
    }

    override fun onClose(player: JukeboxPlayer) {
        super.onClose(player)
        player.setCraftingGridInventory(JukeboxSmallCraftingGridInventory(player))
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 32)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 32, item, sendContents)
    }
}