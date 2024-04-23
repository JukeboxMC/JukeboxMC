package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StoneBlockSlab3
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.StoneSlabType3
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStoneBlockSlab3(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    StoneBlockSlab3 {

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
            if (targetBlock is BlockStoneBlockSlab3 && targetBlock.getVerticalHalf() == VerticalHalf.TOP && targetBlock.getStoneSlabType3() == this.getStoneSlabType3()) {
                world.setBlock(
                    blockPosition,
                    Block.create<BlockDoubleStoneBlockSlab3>(BlockType.DOUBLE_STONE_BLOCK_SLAB3)
                        .setStoneSlabType3(this.getStoneSlabType3())
                )
                return true
            } else if (block is BlockStoneBlockSlab3 && block.getStoneSlabType3() == this.getStoneSlabType3()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab3>(BlockType.DOUBLE_STONE_BLOCK_SLAB3)
                        .setStoneSlabType3(this.getStoneSlabType3())
                )
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockStoneBlockSlab3 && targetBlock.getVerticalHalf() != VerticalHalf.TOP && targetBlock.getStoneSlabType3() == this.getStoneSlabType3()) {
                world.setBlock(
                    blockPosition,
                    Block.create<BlockDoubleStoneBlockSlab3>(BlockType.DOUBLE_STONE_BLOCK_SLAB3)
                        .setStoneSlabType3(this.getStoneSlabType3())
                )
                return true
            } else if (block is BlockStoneBlockSlab3 && block.getStoneSlabType3() == this.getStoneSlabType3()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab3>(BlockType.DOUBLE_STONE_BLOCK_SLAB3)
                        .setStoneSlabType3(this.getStoneSlabType3())
                )
                return true
            }
        } else {
            if (block is BlockStoneBlockSlab3 && block.getStoneSlabType3() == this.getStoneSlabType3()) {
                world.setBlock(
                    placePosition,
                    Block.create<BlockDoubleStoneBlockSlab3>(BlockType.DOUBLE_STONE_BLOCK_SLAB3)
                        .setStoneSlabType3(this.getStoneSlabType3())
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

    override fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab3 {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getStoneSlabType3(): StoneSlabType3 {
        return StoneSlabType3.valueOf(this.getStringState("stone_slab_type_3"))
    }

    override fun setStoneSlabType3(value: StoneSlabType3): StoneBlockSlab3 {
        return this.setState("stone_slab_type_3", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}