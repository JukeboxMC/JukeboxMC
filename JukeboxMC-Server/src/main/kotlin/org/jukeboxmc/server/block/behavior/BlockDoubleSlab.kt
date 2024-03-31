package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleSlab
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleSlab {

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): BlockDoubleSlab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return when (this.getType()) {
            BlockType.BLACKSTONE_DOUBLE_SLAB, BlockType.POLISHED_BLACKSTONE_DOUBLE_SLAB,
            BlockType.POLISHED_BLACKSTONE_BRICK_DOUBLE_SLAB, BlockType.DOUBLE_CUT_COPPER_SLAB, BlockType.EXPOSED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WEATHERED_DOUBLE_CUT_COPPER_SLAB, BlockType.OXIDIZED_DOUBLE_CUT_COPPER_SLAB, BlockType.WAXED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WAXED_EXPOSED_DOUBLE_CUT_COPPER_SLAB, BlockType.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB,
            BlockType.WAXED_OXIDIZED_DOUBLE_CUT_COPPER_SLAB, BlockType.COBBLED_DEEPSLATE_DOUBLE_SLAB, BlockType.POLISHED_DEEPSLATE_DOUBLE_SLAB,
            BlockType.DEEPSLATE_TILE_DOUBLE_SLAB, BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB, BlockType.MUD_BRICK_DOUBLE_SLAB -> {
                this.createItemDrop(item,
                    Item.create(ItemType.valueOf(this.getType().name.replace("DOUBLE_", "")))
                        .apply { this.setAmount(2) },
                    toolType = ToolType.PICKAXE
                )
            }

            else -> mutableListOf()
        }
    }

    override fun toItem(): Item {
        return Item.create(ItemType.valueOf(this.getType().name.replace("DOUBLE_", "")))
    }

}