package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleStoneBlockSlab
import org.jukeboxmc.api.block.data.StoneSlabType
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleStoneBlockSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab {

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getStoneSlabType(): StoneSlabType {
        return StoneSlabType.valueOf(this.getStringState("stone_slab_type"))
    }

    override fun setStoneSlabType(value: StoneSlabType): BlockDoubleStoneBlockSlab {
        return this.setState("stone_slab_type", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.DOUBLE_STONE_BLOCK_SLAB -> {
                this.createItemDrop(item, Item.create(this.toType()).apply {
                    this.setAmount(2)
                })
            }

            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create(this.toType())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }

    private fun toType(): ItemType {
        return when (this.getStoneSlabType()) {
            StoneSlabType.SMOOTH_STONE -> ItemType.SMOOTH_STONE_SLAB
            StoneSlabType.SANDSTONE -> ItemType.SANDSTONE_SLAB
            StoneSlabType.WOOD -> ItemType.OAK_SLAB
            StoneSlabType.COBBLESTONE -> ItemType.COBBLESTONE_SLAB
            StoneSlabType.BRICK -> ItemType.BRICK_SLAB
            StoneSlabType.STONE_BRICK -> ItemType.STONE_BRICK_SLAB
            StoneSlabType.QUARTZ -> ItemType.QUARTZ_SLAB
            else -> ItemType.NETHER_BRICK_SLAB
        }
    }
}