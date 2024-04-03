package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleWoodenSlab
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleWoodenSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleWoodenSlab {

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): BlockDoubleWoodenSlab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.OAK_DOUBLE_SLAB, BlockType.SPRUCE_DOUBLE_SLAB, BlockType.BIRCH_DOUBLE_SLAB,
            BlockType.JUNGLE_DOUBLE_SLAB, BlockType.ACACIA_DOUBLE_SLAB, BlockType.DARK_OAK_DOUBLE_SLAB,
            BlockType.MANGROVE_DOUBLE_SLAB, BlockType.CHERRY_DOUBLE_SLAB, BlockType.BAMBOO_DOUBLE_SLAB,
            BlockType.BAMBOO_MOSAIC_DOUBLE_SLAB, BlockType.CRIMSON_DOUBLE_SLAB, BlockType.WARPED_DOUBLE_SLAB -> {
                this.createItemDrop(item, Item.create(ItemType.valueOf(this.getType().name.replace("_DOUBLE", "")), 2))
            }
            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create(ItemType.valueOf(this.getType().name.replace("_DOUBLE", "")))
    }
}