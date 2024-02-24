package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntityShulkerBox
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.ShulkerBoxInventory
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.inventory.JukeboxShulkerBoxInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBlockEntityShulkerBox(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityShulkerBox, InventoryHolder {

    private var facing: Byte = 0
    private var undyed: Boolean = false

    private val shulkerBoxInventory = JukeboxShulkerBoxInventory(this)

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.shulkerBoxInventory, blockPosition)
        return true
    }

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        this.facing = compound.getByte("facing", 0)
        this.undyed = compound.getBoolean("isUndyed", false)
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        compound.putByte("facing", this.facing)
        compound.putBoolean("isUndyed", this.undyed)
        return compound
    }

    override fun getFacing(): Byte {
        return this.facing
    }

    override fun setFacing(facing: Byte) {
        this.facing = facing
    }

    override fun isUndyed(): Boolean {
        return this.undyed
    }

    override fun setUndyed(undyed: Boolean) {
        this.undyed = undyed
    }

    override fun getShulkerBoxInventory(): ShulkerBoxInventory {
        return this.shulkerBoxInventory
    }

}