package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Grindstone
import org.jukeboxmc.api.block.data.Attachment
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockGrindstone(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Grindstone {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (blockFace === BlockFace.UP) {
            this.setAttachment(Attachment.STANDING)
            this.setDirection(player.getDirection().opposite())
        } else if (blockFace === BlockFace.DOWN) {
            this.setAttachment(Attachment.HANGING)
            this.setDirection(player.getDirection().opposite())
        } else {
            this.setAttachment(Attachment.SIDE)
            this.setDirection(blockFace.toDirection())
        }
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }
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
        player.openInventory(player.getGrindstoneInventory(), blockPosition)
        return true
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.SOUTH
            1 -> Direction.WEST
            2 -> Direction.NORTH
            else -> Direction.EAST
        }
    }

    override fun setDirection(value: Direction): Grindstone {
        return when (value) {
            Direction.SOUTH -> this.setState("direction", 0)
            Direction.WEST -> this.setState("direction", 1)
            Direction.NORTH -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun getAttachment(): Attachment {
        return Attachment.valueOf(this.getStringState("attachment"))
    }

    override fun setAttachment(value: Attachment): Grindstone {
        return this.setState("attachment", value.name.lowercase())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}