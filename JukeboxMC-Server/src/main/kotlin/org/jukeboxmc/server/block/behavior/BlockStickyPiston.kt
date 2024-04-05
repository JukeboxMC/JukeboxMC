package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.StickyPiston
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import kotlin.math.abs

class BlockStickyPiston(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    StickyPiston {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (abs(player.getX() - getLocation().getX()) < 2 && abs(player.getZ() - getLocation().getZ()) < 2) {
            val y = (player.getY() + player.getEyeHeight()).toDouble()
            if (y - this.getY() > 2) {
                this.setFacingDirection(BlockFace.UP)
            } else if (this.getY() - y > 0) {
                this.setFacingDirection(BlockFace.DOWN)
            } else {
                this.setFacingDirection(player.getDirection().toBlockFace())
            }
        } else {
            this.setFacingDirection(player.getDirection().toBlockFace())
        }
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): StickyPiston {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}