package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.Door
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockDoor(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Door {

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

        val blockAbove = Block.create(this.getType()) as BlockDoor
        val upperLocation = placePosition.add(0F, 1F, 0F)
        blockAbove.setLocation(
            Location(
                world,
                upperLocation.getBlockX(),
                upperLocation.getBlockY(),
                upperLocation.getBlockZ()
            )
        )
        blockAbove.setDirection(this.getDirection())
        blockAbove.setUpperBlock(true)
        blockAbove.setOpen(false)

        val blockLeft =
            world.getBlock(placePosition).getRelative(player.getDirection().getLeftDirection().toBlockFace())
        if (blockLeft.getType() == this.getType()) {
            blockAbove.setDoorHinge(true)
            this.setDoorHinge(true)
        } else {
            blockAbove.setDoorHinge(false)
            this.setDoorHinge(false)
        }

        this.setUpperBlock(false)
        this.setOpen(false)

        world.setBlock(upperLocation, blockAbove)
        world.setBlock(placePosition, this)

        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition.add(0f, 1f, 0f), block, 1, false)
            world.setBlock(placePosition, block, 1, false)
        }
        return true
    }

    override fun onBlockBreak(breakLocation: Vector) {
        val block = this.getWorld().getBlock(breakLocation, 1)
        if (this.isUpperBlock()) {
            this.getWorld().setBlock(this.getLocation().subtract(0F, 1F, 0F), block)
            this.getWorld().setBlock(this.getLocation().subtract(0F, 1F, 0F), 1, AIR)
        } else {
            this.getWorld().setBlock(this.getLocation().add(0F, 1F, 0F), block)
            this.getWorld().setBlock(this.getLocation().add(0F, 1F, 0F), 1, AIR)
        }
        this.getWorld().setBlock(this.getLocation(), block)
        this.getWorld().setBlock(this.getLocation(), 1, AIR)
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
        player.playSound(
            this.getLocation(),
            if (open) Sound.CLOSE_WOODEN_DOOR else Sound.OPEN_WOODEN_DOOR,
            1F,
            1F,
            true
        )
        return true
    }

    private fun toggleOpen(open: Boolean) {
        if (open == this.isOpen()) return

        val blockDown: BlockDoor
        val blockUp: BlockDoor
        if (this.isUpperBlock()) {
            blockDown = this.getRelative(BlockFace.DOWN) as BlockDoor
            blockUp = this
        } else {
            blockDown = this
            blockUp = this.getRelative(BlockFace.UP) as BlockDoor
        }

        blockUp.setOpen(open)
        this.getWorld().setBlock(blockUp.getLocation(), blockUp)

        blockDown.setOpen(open)
        this.getWorld().setBlock(blockDown.getLocation(), blockDown)

    }

    override fun isOpen(): Boolean {
        return this.getBooleanState("open_bit")
    }

    override fun setOpen(value: Boolean): BlockDoor {
        return this.setState("open_bit", value.toByte())
    }

    override fun isUpperBlock(): Boolean {
        return this.getBooleanState("upper_block_bit")
    }

    override fun setUpperBlock(value: Boolean): BlockDoor {
        return this.setState("upper_block_bit", value.toByte())
    }

    override fun isDoorHinge(): Boolean {
        return this.getBooleanState("door_hinge_bit")
    }

    override fun setDoorHinge(value: Boolean): BlockDoor {
        return this.setState("door_hinge_bit", value.toByte())
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.EAST
            1 -> Direction.SOUTH
            2 -> Direction.WEST
            else -> Direction.NORTH
        }
    }

    override fun setDirection(value: Direction): BlockDoor {
        return when (value) {
            Direction.EAST -> this.setState("direction", 0)
            Direction.SOUTH -> this.setState("direction", 1)
            Direction.WEST -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}