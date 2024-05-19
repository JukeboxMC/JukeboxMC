package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.LoomInventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.block.behavior.BlockLoom
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxLoomInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 4),
    LoomInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.LOOM
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.LOOM
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 9)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 9, item, sendContents)
    }

    override fun getBanner(): Item {
        return this.getItem(9)
    }

    override fun getMaterial(): Item {
        return this.getItem(10)
    }

    override fun getPattern(): Item {
        return this.getItem(11)
    }
}