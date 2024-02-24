package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Lever
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.LeverDirection
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockLever(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Lever {

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
        if (block.isTransparent()) return false
        this.setLeverDirection(LeverDirection.forDirection(blockFace, player.getDirection().opposite()))
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isOpen(): Boolean {
       return this.getBooleanState("open_bit")
   }

   override fun setOpen(value: Boolean): Lever {
       return this.setState("open_bit", value.toByte())
   }

   override fun getLeverDirection(): LeverDirection {
       return LeverDirection.valueOf(this.getStringState("lever_direction"))
   }

   override fun setLeverDirection(value: LeverDirection): Lever {
       return this.setState("lever_direction", value.name.lowercase())
   }
}