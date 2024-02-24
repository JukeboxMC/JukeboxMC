package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.AnvilInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxAnvilInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3), AnvilInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.ANVIL
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.ANVIL
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 1)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 1, item, sendContents)
    }
}