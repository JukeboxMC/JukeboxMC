package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StoneBlockSlab
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.StoneSlabType
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStoneBlockSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    StoneBlockSlab {

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

        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }

        if (blockFace === BlockFace.DOWN) {
            if (targetBlock is BlockStoneBlockSlab && targetBlock.getVerticalHalf() == VerticalHalf.TOP && targetBlock.getType() == this.getType()) {
                world.setBlock(
                    blockPosition,
                    Block.create<BlockDoubleStoneBlockSlab>(BlockType.DOUBLE_STONE_BLOCK_SLAB)
                        .setStoneSlabType(this.getStoneSlabType())
                )
                return true
            } else if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab>(BlockType.DOUBLE_STONE_BLOCK_SLAB)
                        .setStoneSlabType(this.getStoneSlabType())
                )
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockStoneBlockSlab && targetBlock.getVerticalHalf() != VerticalHalf.TOP && targetBlock.getType() == this.getType()) {
                world.setBlock(
                    blockPosition,
                    Block.create<BlockDoubleStoneBlockSlab>(BlockType.DOUBLE_STONE_BLOCK_SLAB)
                        .setStoneSlabType(this.getStoneSlabType())
                )
                return true
            } else if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab>(BlockType.DOUBLE_STONE_BLOCK_SLAB)
                        .setStoneSlabType(this.getStoneSlabType())
                )
                return true
            }
        } else {
            if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab>(BlockType.DOUBLE_STONE_BLOCK_SLAB)
                        .setStoneSlabType(this.getStoneSlabType())
                )
                return true
            } else {
                this.setVerticalHalf(
                    if (clickedPosition.getY() > 0.5 && !world.getBlock(blockPosition).toJukeboxBlock()
                            .canBeReplaced(this)
                    ) VerticalHalf.TOP else VerticalHalf.BOTTOM
                )
            }
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getVerticalHalf(): VerticalHalf {
        return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
    }

    override fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

    private fun getStoneSlabType(): StoneSlabType {
        return when (this.getType()) {
            BlockType.SMOOTH_STONE_SLAB -> StoneSlabType.SMOOTH_STONE
            BlockType.SANDSTONE_SLAB -> StoneSlabType.SANDSTONE
            BlockType.OAK_SLAB -> StoneSlabType.WOOD
            BlockType.COBBLESTONE_SLAB -> StoneSlabType.COBBLESTONE
            BlockType.BRICK_SLAB -> StoneSlabType.BRICK
            BlockType.STONE_BRICK_SLAB -> StoneSlabType.STONE_BRICK
            BlockType.QUARTZ_SLAB -> StoneSlabType.QUARTZ
            else -> StoneSlabType.NETHER_BRICK
        }
    }
}