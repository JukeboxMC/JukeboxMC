package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.StoneBlockSlab2
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.StoneSlabType2
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStoneBlockSlab2(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), StoneBlockSlab2 {

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
            if (targetBlock is BlockStoneBlockSlab2 && targetBlock.getVerticalHalf() == VerticalHalf.TOP && targetBlock.getStoneSlabType2() == this.getStoneSlabType2()) {
                world.setBlock(blockPosition, Block.create<BlockDoubleStoneBlockSlab2>(BlockType.DOUBLE_STONE_BLOCK_SLAB2).setStoneSlabType2(this.getStoneSlabType2()))
                return true
            } else if (block is BlockStoneBlockSlab2 && block.getStoneSlabType2() == this.getStoneSlabType2()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab2>(BlockType.DOUBLE_STONE_BLOCK_SLAB2).setStoneSlabType2(this.getStoneSlabType2()))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockStoneBlockSlab2 && targetBlock.getVerticalHalf() != VerticalHalf.TOP && targetBlock.getStoneSlabType2() == this.getStoneSlabType2()) {
                world.setBlock(blockPosition, Block.create<BlockDoubleStoneBlockSlab2>(BlockType.DOUBLE_STONE_BLOCK_SLAB2).setStoneSlabType2(this.getStoneSlabType2()))
                return true
            } else if (block is BlockStoneBlockSlab2 && block.getStoneSlabType2() == this.getStoneSlabType2()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab2>(BlockType.DOUBLE_STONE_BLOCK_SLAB2).setStoneSlabType2(this.getStoneSlabType2()))
                return true
            }
        } else {
            if (block is BlockStoneBlockSlab2 && block.getStoneSlabType2() == this.getStoneSlabType2()) {
                world.setBlock(placePosition, Block.create<BlockDoubleStoneBlockSlab2>(BlockType.DOUBLE_STONE_BLOCK_SLAB2).setStoneSlabType2(this.getStoneSlabType2()))
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

   override fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab2 {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType2(): StoneSlabType2 {
       return StoneSlabType2.valueOf(this.getStringState("stone_slab_type_2"))
   }

   override fun setStoneSlabType2(value: StoneSlabType2): StoneBlockSlab2 {
       return this.setState("stone_slab_type_2", value.name.lowercase())
   }

    override fun getDrops(item: Item): MutableList<Item> {
        return this.createItemDrop(item, this.toItem())
    }
}