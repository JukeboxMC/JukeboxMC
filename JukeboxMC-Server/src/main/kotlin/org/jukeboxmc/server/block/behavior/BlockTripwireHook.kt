package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TripwireHook
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.isOneOf
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockTripwireHook(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    TripwireHook {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (blockFace.isOneOf(BlockFace.UP, BlockFace.DOWN)) return false
        this.setDirection(blockFace.toDirection())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.SOUTH
            1 -> Direction.WEST
            2 -> Direction.NORTH
            else -> Direction.EAST
        }
    }

    override fun setDirection(value: Direction): TripwireHook {
        return when (value) {
            Direction.SOUTH -> this.setState("direction", 0)
            Direction.WEST -> this.setState("direction", 1)
            Direction.NORTH -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun isAttached(): Boolean {
        return this.getBooleanState("attached_bit")
    }

    override fun setAttached(value: Boolean): TripwireHook {
        return this.setState("attached_bit", value.toByte())
    }

    override fun isPowered(): Boolean {
        return this.getBooleanState("powered_bit")
    }

    override fun setPowered(value: Boolean): TripwireHook {
        return this.setState("powered_bit", value.toByte())
    }
}