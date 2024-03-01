package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.WoodenSlab
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.VerticalHalf
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

        if (blockFace === BlockFace.DOWN) {
            if (targetBlock is BlockWoodenSlab && targetBlock.getVerticalHalf() == VerticalHalf.TOP && this.getWoodType(targetBlock.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleWoodenSlab>(this.getType()))
                return true
            } else if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(this.getType()))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockWoodenSlab && targetBlock.getVerticalHalf() != VerticalHalf.TOP && this.getWoodType(targetBlock.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleWoodenSlab>(this.getType()))
                return true
            } else if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(this.getType()))
                return true
            }
        } else {
            if (block is BlockWoodenSlab && this.getWoodType(block.getType()) == this.getWoodType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleWoodenSlab>(this.getType()))
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

    private fun getWoodType(type: BlockType): String {
        return when (type) {
            BlockType.OAK_SLAB, BlockType.OAK_DOUBLE_SLAB -> "oak"
            BlockType.SPRUCE_SLAB, BlockType.SPRUCE_DOUBLE_SLAB -> "spruce"
            BlockType.BIRCH_SLAB, BlockType.BIRCH_DOUBLE_SLAB -> "birch"
            BlockType.JUNGLE_SLAB, BlockType.JUNGLE_DOUBLE_SLAB -> "jungle"
            BlockType.ACACIA_SLAB, BlockType.ACACIA_DOUBLE_SLAB -> "acacia"
            BlockType.DARK_OAK_SLAB, BlockType.DARK_OAK_DOUBLE_SLAB -> "dark_oak"
            else -> ""
        }
    }
}