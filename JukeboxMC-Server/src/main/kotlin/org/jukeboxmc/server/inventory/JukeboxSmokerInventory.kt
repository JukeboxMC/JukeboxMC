package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.SmokerInventory
import org.jukeboxmc.server.blockentity.JukeboxBlockEntitySmoker

class JukeboxSmokerInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3), SmokerInventory {

    override fun getInventoryHolder(): JukeboxBlockEntitySmoker {
        return super.getInventoryHolder() as JukeboxBlockEntitySmoker
    }

    override fun getType(): InventoryType {
        return InventoryType.SMOKER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.SMOKER
    }
}