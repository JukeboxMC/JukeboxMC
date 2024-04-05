package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.WoodenSlab
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockWoodenSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), WoodenSlab {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val targetBlock = world.getBlock(blockPosition)
        val block = world.getBlock(placePosition)

        val doubleWoodenType = when(this.getType()) {
            BlockType.OAK_SLAB -> BlockType.OAK_DOUBLE_SLAB
            BlockType.SPRUCE_SLAB -> BlockType.SPRUCE_DOUBLE_SLAB
            BlockType.BIRCH_SLAB -> BlockType.BIRCH_DOUBLE_SLAB
            BlockType.JUNGLE_SLAB -> BlockType.JUNGLE_DOUBLE_SLAB
            BlockType.ACACIA_SLAB -> BlockType.ACACIA_DOUBLE_SLAB
            BlockType.DARK_OAK_SLAB -> BlockType.DARK_OAK_DOUBLE_SLAB
            BlockType.MANGROVE_SLAB -> BlockType.MANGROVE_DOUBLE_SLAB
            BlockType.CHERRY_SLAB -> BlockType.CHERRY_DOUBLE_SLAB
            BlockType.BAMBOO_SLAB -> BlockType.BAMBOO_DOUBLE_SLAB
            BlockType.BAMBOO_MOSAIC_SLAB -> BlockType.BAMBOO_MOSAIC_DOUBLE_SLAB
            BlockType.CRIMSON_SLAB -> BlockType.CRIMSON_DOUBLE_SLAB
            BlockType.WARPED_SLAB -> BlockType.WARPED_DOUBLE_SLAB
            else -> BlockType.AIR
        }

        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }

        if (blockFace === BlockFace.DOWN) {
            if (targetBlock is BlockWoodenSlab && targetBlock.getVerticalHalf() == VerticalHalf.TOP && this.getWoodType(targetBlock.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleWoodenSlab>(doubleWoodenType))
                return true
            } else if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(doubleWoodenType))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockWoodenSlab && targetBlock.getVerticalHalf() != VerticalHalf.TOP && this.getWoodType(targetBlock.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleWoodenSlab>(doubleWoodenType))
                return true
            } else if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(doubleWoodenType))
                return true
            }
        } else {
            if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(doubleWoodenType))
                return true
            } else {
                this.setVerticalHalf(if (clickedPosition.getY() > 0.5 && !world.getBlock(blockPosition).toJukeboxBlock().canBeReplaced(this)) VerticalHalf.TOP else VerticalHalf.BOTTOM)
            }
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): WoodenSlab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }

    private fun getWoodType(type: BlockType): String {
        return when (type) {
            BlockType.OAK_SLAB, BlockType.OAK_DOUBLE_SLAB -> "oak"
            BlockType.SPRUCE_SLAB, BlockType.SPRUCE_DOUBLE_SLAB -> "spruce"
            BlockType.BIRCH_SLAB, BlockType.BIRCH_DOUBLE_SLAB -> "birch"
            BlockType.JUNGLE_SLAB, BlockType.JUNGLE_DOUBLE_SLAB -> "jungle"
            BlockType.ACACIA_SLAB, BlockType.ACACIA_DOUBLE_SLAB -> "acacia"
            BlockType.DARK_OAK_SLAB, BlockType.DARK_OAK_DOUBLE_SLAB -> "dark_oak"
            BlockType.MANGROVE_SLAB, BlockType.MANGROVE_DOUBLE_SLAB -> "mangrove"
            BlockType.CHERRY_SLAB, BlockType.CHERRY_DOUBLE_SLAB -> "cherry"
            BlockType.BAMBOO_SLAB, BlockType.BAMBOO_DOUBLE_SLAB -> "bamboo"
            BlockType.BAMBOO_MOSAIC_SLAB, BlockType.BAMBOO_MOSAIC_DOUBLE_SLAB -> "bamboo_mosaic"
            BlockType.CRIMSON_SLAB, BlockType.CRIMSON_DOUBLE_SLAB -> "crimson"
            BlockType.WARPED_SLAB, BlockType.WARPED_DOUBLE_SLAB -> "warped"
            else -> ""
        }
    }
}