package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Stairs
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.WeirdoDirection
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

open class BlockStairs(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Stairs {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setWeirdoDirection(player.getDirection().toCrossDirection())
        if (clickedPosition.getY() > 0.5 && blockFace !== BlockFace.UP || blockFace === BlockFace.DOWN) {
            this.setUpsideDown(true)
        } else {
            this.setUpsideDown(false)
        }
        world.setBlock(placePosition, this)
        return true
    }

   override fun isUpsideDown(): Boolean {
       return this.getBooleanState("upside_down_bit")
   }

   override fun setUpsideDown(value: Boolean): Stairs {
       return this.setState("upside_down_bit", value.toByte())
   }

   override fun getWeirdoDirection(): WeirdoDirection {
       return WeirdoDirection.entries[this.getIntState("weirdo_direction")]
   }

   override fun setWeirdoDirection(value: WeirdoDirection): Stairs {
       return this.setState("weirdo_direction", value.ordinal)
   }
}