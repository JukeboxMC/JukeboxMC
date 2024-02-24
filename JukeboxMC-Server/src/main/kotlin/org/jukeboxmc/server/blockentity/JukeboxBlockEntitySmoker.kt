package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.cloudburstmc.nbt.NbtType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntitySmoker
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.SmokerInventory
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.inventory.JukeboxSmokerInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class JukeboxBlockEntitySmoker(blockEntityType: BlockEntityType, block: JukeboxBlock) :
    JukeboxBlockEntity(blockEntityType, block), BlockEntitySmoker, InventoryHolder {

        private val smokerInventory = JukeboxSmokerInventory(this)

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.smokerInventory, blockPosition)
        return true
    }

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        val items = compound.getList("Items", NbtType.COMPOUND)
        for (nbtMap in items) {
            val item = toItem(nbtMap)
            val slot = nbtMap.getByte("Slot", 127.toByte())
            if (slot.toInt() == 127) {
                this.smokerInventory.addItem(false, item)
            } else {
                this.smokerInventory.setItem(slot.toInt(), item, false)
            }
        }
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        val itemsCompoundList: MutableList<NbtMap> = ArrayList()
        for (slot in 0 until this.smokerInventory.getSize()) {
            val itemCompound = NbtMap.builder()
            val item = this.smokerInventory.getItem(slot)
            itemCompound.putByte("Slot", slot.toByte())
            fromItem(item, itemCompound)
            itemsCompoundList.add(itemCompound.build())
        }
        compound.putList("Items", NbtType.COMPOUND, itemsCompoundList)
        return compound
    }

    override fun getSmokerInventory(): SmokerInventory {
        return this.smokerInventory
    }

}