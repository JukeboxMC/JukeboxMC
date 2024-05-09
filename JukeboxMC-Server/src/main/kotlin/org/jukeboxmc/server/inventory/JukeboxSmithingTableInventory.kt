package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.SmithingTableInventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxSmithingTableInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3),
    SmithingTableInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.SMITHING_TABLE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.SMITHING_TABLE
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 51)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 51, item, sendContents)
    }

    override fun getArmor(): Item {
        return this.getItem(51)
    }

    override fun getMaterial(): Item {
        return this.getItem(52)
    }

    override fun getTemplate(): Item {
        return this.getItem(53)
    }
}