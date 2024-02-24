package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.inventory.StoneCutterInventory
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxStoneCutterInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 2), StoneCutterInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.STONE_CUTTER
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.STONECUTTER
    }

}