package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Cactus
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCactus(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Cactus {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (blockDown.getType() != BlockType.SAND) return false
        val blockNorth = this.getRelative(Direction.NORTH)
        val blockEast = this.getRelative(Direction.EAST)
        val blockSouth = this.getRelative(Direction.SOUTH)
        val blockWest = this.getRelative(Direction.WEST)

        if (blockNorth.canBeFlowedInto() && blockEast.canBeFlowedInto() && blockSouth.canBeFlowedInto() && blockWest.canBeFlowedInto()) {
            val block = world.getBlock(placePosition)
            if (block is BlockWater && block.getLiquidDepth() == 0) {
                world.setBlock(placePosition, block, 1, false)
            }
            world.setBlock(placePosition, this)
            return true
        }
        return false
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (updateReason == UpdateReason.NORMAL) {
            if (blockDown.getType() == BlockType.AIR || blockDown.getType() == BlockType.FIRE) { //TODO Add liquid check
                this.getWorld().setBlock(this.getLocation(), AIR)
                this.getWorld().dropItemNaturally(this.getLocation(), this.toItem())
            }
        }
        return -1
    }

    override fun getAge(): Int {
        return this.getIntState("age")
    }

    override fun setAge(value: Int): BlockCactus {
        return this.setState("age", value)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}