package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.BeaconInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityBeacon

class JukeboxBeaconInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 1), BeaconInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityBeacon {
        return super.getInventoryHolder() as JukeboxBlockEntityBeacon
    }

    override fun getType(): InventoryType {
        return InventoryType.BEACON
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.BEACON
    }
}