package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.DispenserInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityDispenser

class JukeboxDispenserInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 9),
    DispenserInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityDispenser {
        return super.getInventoryHolder() as JukeboxBlockEntityDispenser
    }

    override fun getType(): InventoryType {
        return InventoryType.DISPENSER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.DISPENSER
    }
}