package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Vine
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockVine(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Vine {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(blockPosition)
        if (block.isSolid() && blockFace.getHorizontalIndex() != -1) {
            this.setVineDirection(1 shl blockFace.opposite().getHorizontalIndex())
            return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
        }
        return false
    }

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getVineDirection(): Int {
       return this.getIntState("vine_direction_bits")
   }

   override fun setVineDirection(value: Int): Vine {
       return this.setState("vine_direction_bits", value)
   }
}