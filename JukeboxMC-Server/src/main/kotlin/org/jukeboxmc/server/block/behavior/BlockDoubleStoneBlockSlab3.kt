package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleStoneBlockSlab3
import org.jukeboxmc.api.block.data.StoneSlabType3
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.behavior.ItemStoneBlockSlab3

class BlockDoubleStoneBlockSlab3(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab3 {

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab3 {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getStoneSlabType3(): StoneSlabType3 {
        return StoneSlabType3.valueOf(this.getStringState("stone_slab_type_3"))
    }

    override fun setStoneSlabType3(value: StoneSlabType3): BlockDoubleStoneBlockSlab3 {
        return this.setState("stone_slab_type_3", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.DOUBLE_STONE_BLOCK_SLAB3 -> {
                this.createItemDrop(item, Item.create<ItemStoneBlockSlab3>(ItemType.STONE_BLOCK_SLAB3).apply {
                    this.setAmount(2)
                    this.setStoneSlabType3(this@BlockDoubleStoneBlockSlab3.getStoneSlabType3())
                })
            }

            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create<ItemStoneBlockSlab3>(ItemType.STONE_BLOCK_SLAB3).apply {
            this.setStoneSlabType3(this@BlockDoubleStoneBlockSlab3.getStoneSlabType3())
        }
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}