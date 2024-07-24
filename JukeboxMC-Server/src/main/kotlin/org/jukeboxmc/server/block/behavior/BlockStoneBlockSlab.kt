package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StoneBlockSlab
import org.jukeboxmc.api.block.data.BlockFace
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
                    Block.create(this.toDoubleType())
                )
                return true
            } else if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create(this.toDoubleType())
                )
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockStoneBlockSlab && targetBlock.getVerticalHalf() != VerticalHalf.TOP && targetBlock.getType() == this.getType()) {
                world.setBlock(
                    blockPosition,
                    Block.create(this.toDoubleType())
                )
                return true
            } else if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create(this.toDoubleType())
                )
                return true
            }
        } else {
            if (block is BlockStoneBlockSlab && block.getType() == this.getType()) {
                world.setBlock(
                    placePosition,
                    Block.create(this.toDoubleType())
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

    private fun toDoubleType(): BlockType {
        return when (this.getType()) {
            BlockType.SMOOTH_STONE_SLAB -> BlockType.SMOOTH_STONE_DOUBLE_SLAB
            BlockType.SANDSTONE_SLAB -> BlockType.SANDSTONE
            BlockType.OAK_SLAB -> BlockType.OAK_DOUBLE_SLAB
            BlockType.COBBLESTONE_SLAB -> BlockType.COBBLESTONE_DOUBLE_SLAB
            BlockType.BRICK_SLAB -> BlockType.BRICK_DOUBLE_SLAB
            BlockType.STONE_BRICK_SLAB -> BlockType.STONE_BRICK_DOUBLE_SLAB
            BlockType.QUARTZ_SLAB -> BlockType.QUARTZ_DOUBLE_SLAB
            BlockType.NETHER_BRICK_SLAB -> BlockType.NETHER_BRICK_DOUBLE_SLAB
            BlockType.END_STONE_BRICK_SLAB -> BlockType.END_STONE_BRICK_DOUBLE_SLAB
            BlockType.SMOOTH_RED_SANDSTONE_SLAB -> BlockType.SMOOTH_RED_SANDSTONE_DOUBLE_SLAB
            BlockType.POLISHED_ANDESITE_SLAB -> BlockType.POLISHED_ANDESITE_DOUBLE_SLAB
            BlockType.ANDESITE_SLAB -> BlockType.ANDESITE_DOUBLE_SLAB
            BlockType.DIORITE_SLAB -> BlockType.DIORITE_DOUBLE_SLAB
            BlockType.POLISHED_DIORITE_SLAB -> BlockType.POLISHED_DIORITE_DOUBLE_SLAB
            BlockType.GRANITE_SLAB -> BlockType.GRANITE_DOUBLE_SLAB
            BlockType.POLISHED_GRANITE_SLAB -> BlockType.POLISHED_GRANITE_DOUBLE_SLAB
            BlockType.RED_SANDSTONE_SLAB -> BlockType.RED_SANDSTONE_DOUBLE_SLAB
            BlockType.PURPUR_SLAB -> BlockType.PURPUR_DOUBLE_SLAB
            BlockType.PRISMARINE_SLAB -> BlockType.PRISMARINE_DOUBLE_SLAB
            BlockType.DARK_PRISMARINE_SLAB -> BlockType.DARK_PRISMARINE_DOUBLE_SLAB
            BlockType.PRISMARINE_BRICK_SLAB -> BlockType.PRISMARINE_BRICK_DOUBLE_SLAB
            BlockType.SMOOTH_SANDSTONE_SLAB -> BlockType.SMOOTH_SANDSTONE_DOUBLE_SLAB
            BlockType.RED_NETHER_BRICK_SLAB -> BlockType.RED_NETHER_BRICK_DOUBLE_SLAB
            BlockType.MOSSY_COBBLESTONE_SLAB -> BlockType.MOSSY_COBBLESTONE_DOUBLE_SLAB
            BlockType.SMOOTH_QUARTZ_SLAB -> BlockType.SMOOTH_QUARTZ_DOUBLE_SLAB
            BlockType.NORMAL_STONE_SLAB -> BlockType.NORMAL_STONE_DOUBLE_SLAB
            BlockType.CUT_SANDSTONE_SLAB -> BlockType.CUT_SANDSTONE_DOUBLE_SLAB
            else -> BlockType.CUT_RED_SANDSTONE_DOUBLE_SLAB
        }
    }
}