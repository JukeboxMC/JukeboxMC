package org.jukeboxmc.server.blockentity

import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntityEnchantingTable
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.EnchantmentTableInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.inventory.JukeboxEnchantmentTableInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBlockEntityEnchantingTable(blockEntityType: BlockEntityType, block: JukeboxBlock) :
    JukeboxBlockEntity(blockEntityType, block), BlockEntityEnchantingTable, InventoryHolder {

        private val enchantmentTableInventory = JukeboxEnchantmentTableInventory(this)

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.enchantmentTableInventory, blockPosition)
        return true
    }

    override fun getEnchantmentTableInventory(): EnchantmentTableInventory {
        return this.enchantmentTableInventory
    }

}