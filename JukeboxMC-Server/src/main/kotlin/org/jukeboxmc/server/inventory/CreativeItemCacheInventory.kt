package org.jukeboxmc.server.inventory

import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.server.player.JukeboxPlayer

class CreativeItemCacheInventory(
    inventoryHolder: InventoryHolder
) : JukeboxInventory(inventoryHolder, 1) {

    override fun getType(): InventoryType {
        return InventoryType.CREATIVE
    }

    override fun getInventoryHolder(): JukeboxPlayer {
        return super.getInventoryHolder() as JukeboxPlayer
    }

    override fun sendContents(player: JukeboxPlayer) {

    }

    override fun sendContents(slot: Int, player: JukeboxPlayer) {
    }

}