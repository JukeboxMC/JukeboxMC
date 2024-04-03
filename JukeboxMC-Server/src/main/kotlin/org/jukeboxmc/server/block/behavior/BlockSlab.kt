package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Slab
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

open class BlockSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Slab {

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

        val doubleSlabType = when (this.getType()) {
            BlockType.BLACKSTONE_SLAB -> BlockType.BLACKSTONE_DOUBLE_SLAB
            BlockType.POLISHED_BLACKSTONE_SLAB -> BlockType.POLISHED_BLACKSTONE_DOUBLE_SLAB
            BlockType.POLISHED_BLACKSTONE_BRICK_SLAB -> BlockType.POLISHED_BLACKSTONE_BRICK_DOUBLE_SLAB
            BlockType.CUT_COPPER_SLAB -> BlockType.DOUBLE_CUT_COPPER_SLAB
            BlockType.EXPOSED_CUT_COPPER_SLAB -> BlockType.EXPOSED_DOUBLE_CUT_COPPER_SLAB
            BlockType.WEATHERED_CUT_COPPER_SLAB -> BlockType.WEATHERED_DOUBLE_CUT_COPPER_SLAB
            BlockType.OXIDIZED_CUT_COPPER_SLAB -> BlockType.OXIDIZED_DOUBLE_CUT_COPPER_SLAB
            BlockType.WAXED_CUT_COPPER_SLAB -> BlockType.WAXED_DOUBLE_CUT_COPPER_SLAB
            BlockType.WAXED_EXPOSED_CUT_COPPER_SLAB -> BlockType.WAXED_EXPOSED_DOUBLE_CUT_COPPER_SLAB
            BlockType.WAXED_WEATHERED_CUT_COPPER_SLAB -> BlockType.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB
            BlockType.WAXED_OXIDIZED_CUT_COPPER_SLAB -> BlockType.WAXED_OXIDIZED_DOUBLE_CUT_COPPER_SLAB
            BlockType.COBBLED_DEEPSLATE_SLAB -> BlockType.COBBLED_DEEPSLATE_DOUBLE_SLAB
            BlockType.POLISHED_DEEPSLATE_SLAB -> BlockType.POLISHED_DEEPSLATE_DOUBLE_SLAB
            BlockType.DEEPSLATE_TILE_SLAB -> BlockType.DEEPSLATE_TILE_DOUBLE_SLAB
            BlockType.DEEPSLATE_BRICK_SLAB -> BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB
            BlockType.MUD_BRICK_SLAB -> BlockType.MUD_BRICK_DOUBLE_SLAB
            else -> BlockType.AIR
        }

        if (blockFace === BlockFace.DOWN) {
            if (targetBlock is BlockSlab && targetBlock.getVerticalHalf() == VerticalHalf.TOP && this.getStoneSlabType(targetBlock.getType()) == this.getStoneSlabType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleSlab>(doubleSlabType))
                return true
            } else if (block is BlockSlab && this.getStoneSlabType(block.getType()) == this.getStoneSlabType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleSlab>(doubleSlabType))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockSlab && targetBlock.getVerticalHalf() != VerticalHalf.TOP && this.getStoneSlabType(targetBlock.getType()) == this.getStoneSlabType(this.getType())) {
                world.setBlock(blockPosition, Block.create<BlockDoubleSlab>(doubleSlabType))
                return true
            } else if (block is BlockSlab && this.getStoneSlabType(block.getType()) == this.getStoneSlabType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleSlab>(doubleSlabType))
                return true
            }
        } else {
            if (block is BlockSlab && this.getStoneSlabType(block.getType()) == this.getStoneSlabType(this.getType())) {
                world.setBlock(placePosition, Block.create<BlockDoubleSlab>(doubleSlabType))
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

    override fun setVerticalHalf(value: VerticalHalf): Slab {
        return this.setState("minecraft:vertical_half", value.name.lowercase())
    }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }

    private fun getStoneSlabType(type: BlockType): String {
        return when (type) {
            BlockType.BLACKSTONE_SLAB, BlockType.BLACKSTONE_DOUBLE_SLAB -> "blackstone"
            BlockType.POLISHED_BLACKSTONE_SLAB, BlockType.POLISHED_BLACKSTONE_DOUBLE_SLAB -> "polished_blackstone"
            BlockType.POLISHED_BLACKSTONE_BRICK_SLAB, BlockType.POLISHED_BLACKSTONE_BRICK_DOUBLE_SLAB -> "polished_blackstone_brick"
            BlockType.CUT_COPPER_SLAB, BlockType.DOUBLE_CUT_COPPER_SLAB -> "cut_copper"
            BlockType.EXPOSED_CUT_COPPER_SLAB, BlockType.EXPOSED_DOUBLE_CUT_COPPER_SLAB -> "exposed_cut_copper"
            BlockType.WEATHERED_CUT_COPPER_SLAB, BlockType.WEATHERED_DOUBLE_CUT_COPPER_SLAB -> "weathered_cut_copper"
            BlockType.OXIDIZED_CUT_COPPER_SLAB, BlockType.OXIDIZED_DOUBLE_CUT_COPPER_SLAB -> "oxidized_cut_copper"
            BlockType.WAXED_CUT_COPPER_SLAB, BlockType.WAXED_DOUBLE_CUT_COPPER_SLAB -> "waxed_cut_copper"
            BlockType.WAXED_EXPOSED_CUT_COPPER_SLAB, BlockType.WAXED_EXPOSED_DOUBLE_CUT_COPPER_SLAB -> "waxed_exposed_cut_copper"
            BlockType.WAXED_WEATHERED_CUT_COPPER_SLAB, BlockType.WAXED_WEATHERED_DOUBLE_CUT_COPPER_SLAB -> "waxed_weathered_cut_copper"
            BlockType.WAXED_OXIDIZED_CUT_COPPER_SLAB, BlockType.WAXED_OXIDIZED_DOUBLE_CUT_COPPER_SLAB -> "waxed_oxidized_cut_copper"
            BlockType.COBBLED_DEEPSLATE_SLAB, BlockType.COBBLED_DEEPSLATE_DOUBLE_SLAB -> "cobbled_deepslate"
            BlockType.POLISHED_DEEPSLATE_SLAB, BlockType.POLISHED_DEEPSLATE_DOUBLE_SLAB -> "polished_deepslate"
            BlockType.DEEPSLATE_TILE_SLAB, BlockType.DEEPSLATE_TILE_DOUBLE_SLAB -> "deepslate_tile"
            BlockType.DEEPSLATE_BRICK_SLAB, BlockType.DEEPSLATE_BRICK_DOUBLE_SLAB -> "deepslate_brick"
            BlockType.MUD_BRICK_SLAB, BlockType.MUD_BRICK_DOUBLE_SLAB -> "mud_brick"
            else -> ""
        }
    }
}