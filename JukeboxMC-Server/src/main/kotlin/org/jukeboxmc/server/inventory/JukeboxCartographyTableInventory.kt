package org.jukeboxmc.server.inventory

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.jukeboxmc.api.inventory.CartographyTableInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxCartographyTableInventory(inventoryHolder: InventoryHolder) : ContainerInventory(inventoryHolder, 3), CartographyTableInventory {

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun getType(): InventoryType {
        return InventoryType.CARTOGRAPHY_TABLE
    }

    override fun getContainerType(): ContainerType {
        return ContainerType.CARTOGRAPHY
    }
}