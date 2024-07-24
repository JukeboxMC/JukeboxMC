package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoubleStoneBlockSlab
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

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, Item.create(this.toType()).apply {
            this.setAmount(2)
        })
    }

    override fun toItem(): Item {
        return Item.create(this.toType())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }

    private fun toType(): ItemType {
        return when (this.getType()) {
            BlockType.SMOOTH_STONE_DOUBLE_SLAB -> ItemType.SMOOTH_STONE_SLAB
            BlockType.SANDSTONE_DOUBLE_SLAB -> ItemType.SANDSTONE_SLAB
            BlockType.OAK_DOUBLE_SLAB -> ItemType.OAK_SLAB
            BlockType.COBBLESTONE_DOUBLE_SLAB -> ItemType.COBBLESTONE_SLAB
            BlockType.BRICK_DOUBLE_SLAB -> ItemType.BRICK_SLAB
            BlockType.STONE_BRICK_DOUBLE_SLAB -> ItemType.STONE_BRICK_SLAB
            BlockType.QUARTZ_DOUBLE_SLAB -> ItemType.QUARTZ_SLAB
            BlockType.NETHER_BRICK_DOUBLE_SLAB -> ItemType.NETHER_BRICK_SLAB
            BlockType.END_STONE_BRICK_DOUBLE_SLAB -> ItemType.END_STONE_BRICK_SLAB
            BlockType.SMOOTH_RED_SANDSTONE_DOUBLE_SLAB -> ItemType.SMOOTH_RED_SANDSTONE_SLAB
            BlockType.POLISHED_ANDESITE_DOUBLE_SLAB -> ItemType.POLISHED_ANDESITE_SLAB
            BlockType.ANDESITE_DOUBLE_SLAB -> ItemType.ANDESITE_SLAB
            BlockType.DIORITE_DOUBLE_SLAB -> ItemType.DIORITE_SLAB
            BlockType.POLISHED_DIORITE_DOUBLE_SLAB -> ItemType.POLISHED_DIORITE_SLAB
            BlockType.GRANITE_DOUBLE_SLAB -> ItemType.GRANITE_SLAB
            BlockType.POLISHED_GRANITE_DOUBLE_SLAB -> ItemType.POLISHED_GRANITE_SLAB
            BlockType.RED_SANDSTONE_DOUBLE_SLAB -> ItemType.RED_SANDSTONE_SLAB
            BlockType.PURPUR_DOUBLE_SLAB -> ItemType.PURPUR_SLAB
            BlockType.PRISMARINE_DOUBLE_SLAB -> ItemType.PRISMARINE_SLAB
            BlockType.DARK_PRISMARINE_DOUBLE_SLAB -> ItemType.DARK_PRISMARINE_SLAB
            BlockType.PRISMARINE_BRICK_DOUBLE_SLAB -> ItemType.PRISMARINE_BRICK_SLAB
            BlockType.SMOOTH_SANDSTONE_DOUBLE_SLAB -> ItemType.SMOOTH_SANDSTONE_SLAB
            BlockType.RED_NETHER_BRICK_DOUBLE_SLAB -> ItemType.RED_NETHER_BRICK_SLAB
            BlockType.MOSSY_COBBLESTONE_DOUBLE_SLAB -> ItemType.MOSSY_COBBLESTONE_SLAB
            BlockType.SMOOTH_QUARTZ_DOUBLE_SLAB -> ItemType.SMOOTH_QUARTZ_SLAB
            BlockType.NORMAL_STONE_DOUBLE_SLAB -> ItemType.NORMAL_STONE_SLAB
            BlockType.CUT_SANDSTONE_DOUBLE_SLAB -> ItemType.CUT_SANDSTONE_SLAB
            else -> ItemType.CUT_RED_SANDSTONE_SLAB
        }
    }
}