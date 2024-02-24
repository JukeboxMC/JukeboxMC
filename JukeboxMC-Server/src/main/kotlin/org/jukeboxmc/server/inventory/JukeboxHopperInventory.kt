package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.HopperInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityHopper

class JukeboxHopperInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 5),
    HopperInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityHopper {
        return super.getInventoryHolder() as JukeboxBlockEntityHopper
    }

    override fun getType(): InventoryType {
        return InventoryType.HOPPER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.HOPPER
    }

}