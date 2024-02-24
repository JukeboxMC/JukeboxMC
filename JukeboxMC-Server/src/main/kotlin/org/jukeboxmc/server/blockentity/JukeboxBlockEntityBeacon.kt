package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntityBeacon
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.BeaconInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.inventory.JukeboxBeaconInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBlockEntityBeacon(blockEntityType: BlockEntityType, block: JukeboxBlock) :
    JukeboxBlockEntity(blockEntityType, block), BlockEntityBeacon, InventoryHolder {

    private val beaconInventory = JukeboxBeaconInventory(this)

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.beaconInventory, blockPosition)
        return true
    }

    override fun getBeaconInventory(): BeaconInventory {
        return this.beaconInventory
    }

}