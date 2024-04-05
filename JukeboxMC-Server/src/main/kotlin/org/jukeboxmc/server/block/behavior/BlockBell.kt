package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Bell
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.Attachment
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockBell(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Bell {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(placePosition)
        if (block is BlockWater && block.getLiquidDepth() == 0) {
            world.setBlock(placePosition, block, 1, false)
        }

        this.setDirection(player.getDirection().opposite())
        if (blockFace === BlockFace.UP) {
            this.setAttachment(Attachment.STANDING)
        } else if (blockFace === BlockFace.DOWN) {
            this.setAttachment(Attachment.HANGING)
        } else {
            this.setDirection(blockFace.toDirection())
            if (world.getBlock(placePosition).getRelative(blockFace).isSolid() && world.getBlock(placePosition)
                    .getRelative(blockFace.opposite()).isSolid()
            ) {
                this.setAttachment(Attachment.MULTIPLE)
            } else {
                if (world.getBlock(blockPosition).isSolid()) {
                    this.setAttachment(Attachment.SIDE)
                } else {
                    return false
                }
            }
        }
        BlockEntity.create(BlockEntityType.BEEHIVE, this)?.spawn()
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

    override fun setDirection(value: Direction): Bell {
        return when (value) {
            Direction.SOUTH -> this.setState("direction", 0)
            Direction.WEST -> this.setState("direction", 1)
            Direction.NORTH -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun isToggle(): Boolean {
        return this.getBooleanState("toggle_bit")
    }

    override fun setToggle(value: Boolean): BlockBell {
        return this.setState("toggle_bit", value.toByte())
    }

    override fun getAttachment(): Attachment {
        return Attachment.valueOf(this.getStringState("attachment"))
    }

    override fun setAttachment(value: Attachment): BlockBell {
        return this.setState("attachment", value.name.lowercase())
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}