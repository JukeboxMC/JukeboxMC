package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.BrewingStandInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityBrewingStand

class JukeboxBrewingStandInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 5),
    BrewingStandInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityBrewingStand {
        return super.getInventoryHolder() as JukeboxBlockEntityBrewingStand
    }

    override fun getType(): InventoryType {
        return InventoryType.BREWING_STAND
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.BREWING_STAND
    }

}