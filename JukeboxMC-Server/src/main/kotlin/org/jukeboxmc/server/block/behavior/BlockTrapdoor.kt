package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Trapdoor
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockTrapdoor(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Trapdoor {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setDirection(player.getDirection().opposite())
        this.setUpsideDown(clickedPosition.getY() > 0.5 && blockFace != BlockFace.UP || blockFace == BlockFace.DOWN)
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
        val open = this.isOpen()
        this.toggleOpen(!open)
        player.playSound(this.getLocation(), if (open) Sound.CLOSE_WOODEN_TRAPDOOR else Sound.OPEN_WOODEN_TRAPDOOR, 1F, 1F, true)
        return true
    }

    private fun toggleOpen(open: Boolean) {
        if (open == this.isOpen()) return
        this.setOpen(open)
    }

    override fun isOpen(): Boolean {
        return this.getBooleanState("open_bit")
    }

    override fun setOpen(value: Boolean): Trapdoor {
        return this.setState("open_bit", value.toByte())
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.EAST
            1 -> Direction.SOUTH
            2 -> Direction.WEST
            else -> Direction.NORTH
        }
    }

    override fun setDirection(value: Direction): Trapdoor {
        return when (value) {
            Direction.EAST -> this.setState("direction", 0)
            Direction.SOUTH -> this.setState("direction", 1)
            Direction.WEST -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun isUpsideDown(): Boolean {
        return this.getBooleanState("upside_down_bit")
    }

    override fun setUpsideDown(value: Boolean): Trapdoor {
        return this.setState("upside_down_bit", value.toByte())
    }
}