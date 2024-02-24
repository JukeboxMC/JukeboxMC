package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.DropperInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.blockentity.JukeboxBlockEntityDropper

class JukeboxDropperInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 9), DropperInventory {

    override fun getInventoryHolder(): JukeboxBlockEntityDropper {
        return super.getInventoryHolder() as JukeboxBlockEntityDropper
    }

    override fun getType(): InventoryType {
        return InventoryType.DROPPER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.DROPPER
    }

}