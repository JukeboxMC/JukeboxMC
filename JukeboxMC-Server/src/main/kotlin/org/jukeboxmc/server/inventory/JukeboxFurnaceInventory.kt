package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.FurnaceInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityFurnace

class JukeboxFurnaceInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3),
    FurnaceInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityFurnace {
        return super.getInventoryHolder() as JukeboxBlockEntityFurnace
    }

    override fun getType(): InventoryType {
        return InventoryType.FURNACE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.FURNACE
    }
}