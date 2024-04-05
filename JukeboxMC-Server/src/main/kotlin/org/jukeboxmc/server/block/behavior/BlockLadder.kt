package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Ladder
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockLadder(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Ladder {

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
        if (!targetBlock.isTransparent() && blockFace !== BlockFace.UP && blockFace !== BlockFace.DOWN) {
            val block = world.getBlock(placePosition)
            if (block is BlockWater && block.getLiquidDepth() == 0) {
                world.setBlock(placePosition, block, 1, false)
            }
            this.setFacingDirection(blockFace)
            world.setBlock(placePosition, this)
            return true
        }
        return false
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): Ladder {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}