package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.EndRod
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockEndRod(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    EndRod {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setFacingDirection(player.getDirection().toBlockFace().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

   override fun getFacingDirection(): BlockFace {
       return BlockFace.entries[this.getIntState("facing_direction")]
   }

   override fun setFacingDirection(value: BlockFace): BlockEndRod {
       return this.setState("facing_direction", value.ordinal)
   }
}