package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FenceGate
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockFenceGate(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), FenceGate {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setDirection(player.getDirection())
        this.setOpen(false)
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        val playerDirection = player.getDirection()
        val direction = this.getDirection()

        if (playerDirection === Direction.NORTH) {
            if (direction === Direction.NORTH || direction === Direction.SOUTH) {
                this.setDirection(Direction.NORTH)
            }
        } else if (playerDirection === Direction.EAST) {
            if (direction === Direction.EAST || direction === Direction.WEST) {
                this.setDirection(Direction.EAST)
            }
        } else if (playerDirection === Direction.SOUTH) {
            if (direction === Direction.NORTH || direction === Direction.SOUTH) {
                this.setDirection(Direction.SOUTH)
            }
        } else if (playerDirection === Direction.WEST) {
            if (direction === Direction.EAST || direction === Direction.WEST) {
                this.setDirection(Direction.WEST)
            }
        }
        val open = this.isOpen()
        this.setOpen(!open)
        player.playSound(if (open) Sound.OPEN_FENCE_GATE else Sound.CLOSE_FENCE_GATE, 1F, 1F, true)
        return true
    }

    override fun isOpen(): Boolean {
        return this.getBooleanState("open_bit")
    }

    override fun setOpen(value: Boolean): FenceGate {
        return this.setState("open_bit", value.toByte())
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.SOUTH
            1 -> Direction.WEST
            2 -> Direction.NORTH
            else -> Direction.EAST
        }
    }

    override fun setDirection(value: Direction): FenceGate {
        return when (value) {
            Direction.SOUTH -> this.setState("direction", 0)
            Direction.WEST -> this.setState("direction", 1)
            Direction.NORTH -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun isInWall(): Boolean {
        return this.getBooleanState("in_wall_bit")
    }

    override fun setInWall(value: Boolean): FenceGate {
        return this.setState("in_wall_bit", value.toByte())
    }
}