package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.GrindstoneInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxGrindstoneInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3), GrindstoneInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.GRINDSTONE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.GRINDSTONE
    }

    override fun getItem(slot: Int): Item {
        return super.getItem(slot - 16)
    }

    override fun setItem(slot: Int, item: Item, sendContents: Boolean) {
        super.setItem(slot - 16, item, sendContents)
    }

    override fun getInput(): Item {
        return this.getItem(16)
    }

    override fun getAdditional(): Item {
        return this.getItem(17)
    }
}