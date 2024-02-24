package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.LoomInventory
import org.jukeboxmc.server.block.behavior.BlockLoom

class JukeboxLoomInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 4),
    LoomInventory {

    override fun getInventoryHolder(): BlockLoom {
        return super.getInventoryHolder() as BlockLoom
    }

    override fun getType(): InventoryType {
        return InventoryType.LOOM
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.LOOM
    }
}