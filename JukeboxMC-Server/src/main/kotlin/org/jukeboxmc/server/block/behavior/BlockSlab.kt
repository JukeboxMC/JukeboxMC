package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.Slab
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
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

        if (blockFace === BlockFace.DOWN) {
            if (targetBlock is BlockSlab) {
                if (targetBlock.getVerticalHalf() == VerticalHalf.TOP) {
                    world.setBlock(blockPosition, Block.create(this.getType()))
                    return true
                }
            } else if (block is BlockSlab) {
                world.setBlock(placePosition, Block.create(this.getType()))
                return true
            }
        } else if (blockFace === BlockFace.UP) {
            if (targetBlock is BlockSlab) {
                if (targetBlock.getVerticalHalf() != VerticalHalf.TOP) {
                    world.setBlock(blockPosition, Block.create(this.getType()))
                    return true
                }
            } else if (block is BlockSlab) {
                world.setBlock(placePosition, Block.create(this.getType()))
                return true
            }
        } else {
            if (block is BlockSlab) {
                world.setBlock(placePosition, Block.create(this.getType()))
                return true
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
}