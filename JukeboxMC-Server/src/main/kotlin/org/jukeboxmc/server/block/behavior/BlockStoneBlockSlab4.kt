package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StoneBlockSlab4
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.StoneSlabType4
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStoneBlockSlab4(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), StoneBlockSlab4 {

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
            if (targetBlock is BlockStoneBlockSlab4 && targetBlock.getVerticalHalf() == VerticalHalf.TOP && targetBlock.getStoneSlabType4() == this.getStoneSlabType4()) {
                world.setBlock(blockPosition, Block.create<BlockDoubleStoneBlockSlab4>(BlockType.DOUBLE_STONE_BLOCK_SLAB4).setStoneSlabType4(this.getStoneSlabType4()))
                return true
            } else if (block is BlockStoneBlockSlab4 && block.getStoneSlabType4() == this.getStoneSlabType4()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab4>(BlockType.DOUBLE_STONE_BLOCK_SLAB4).setStoneSlabType4(this.getStoneSlabType4()))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockStoneBlockSlab4 && targetBlock.getVerticalHalf() != VerticalHalf.TOP && targetBlock.getStoneSlabType4() == this.getStoneSlabType4()) {
                world.setBlock(blockPosition, Block.create<BlockDoubleStoneBlockSlab4>(BlockType.DOUBLE_STONE_BLOCK_SLAB4).setStoneSlabType4(this.getStoneSlabType4()))
                return true
            } else if (block is BlockStoneBlockSlab4 && block.getStoneSlabType4() == this.getStoneSlabType4()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab4>(BlockType.DOUBLE_STONE_BLOCK_SLAB4).setStoneSlabType4(this.getStoneSlabType4()))
                return true
            }
        } else {
            if (block is BlockStoneBlockSlab4 && block.getStoneSlabType4() == this.getStoneSlabType4()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab4>(BlockType.DOUBLE_STONE_BLOCK_SLAB4).setStoneSlabType4(this.getStoneSlabType4()))
                return true
            } else {
                this.setVerticalHalf(if (clickedPosition.getY() > 0.5 && !world.getBlock(blockPosition).toJukeboxBlock().canBeReplaced(this)) VerticalHalf.TOP else VerticalHalf.BOTTOM )
            }
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab4 {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType4(): StoneSlabType4 {
       return StoneSlabType4.valueOf(this.getStringState("stone_slab_type_4"))
   }

   override fun setStoneSlabType4(value: StoneSlabType4): StoneBlockSlab4 {
       return this.setState("stone_slab_type_4", value.name.lowercase())
   }
}