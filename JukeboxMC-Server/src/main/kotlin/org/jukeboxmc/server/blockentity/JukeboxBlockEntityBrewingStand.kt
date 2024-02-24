package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntityBrewingStand
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.BrewingStandInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.inventory.JukeboxBrewingStandInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBlockEntityBrewingStand(blockEntityType: BlockEntityType, block: JukeboxBlock) :
    JukeboxBlockEntity(blockEntityType, block), BlockEntityBrewingStand, InventoryHolder {

        private val brewingStandInventory = JukeboxBrewingStandInventory(this)

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.brewingStandInventory, blockPosition)
        return true
    }

    override fun getBrewingStandInventory(): BrewingStandInventory {
        return this.brewingStandInventory
    }
}