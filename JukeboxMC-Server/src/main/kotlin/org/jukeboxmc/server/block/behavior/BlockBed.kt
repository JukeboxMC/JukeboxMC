package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Bed
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Color
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityBed
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockBed(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Bed {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockColor = Color.entries[itemInHand.getMeta()]
        val direction = player.getDirection()

        val blockDirection = this.getRelative(direction.toBlockFace()).toJukeboxBlock()
        val directionLocation = blockDirection.getLocation()

        if (blockDirection.canBeReplaced(this) && blockDirection.isTransparent() && !this.getRelative(BlockFace.DOWN)
                .isTransparent() && !directionLocation.getBlock().getRelative(BlockFace.DOWN).isTransparent()
        ) {
            this.setDirection(direction)

            val blockBed: BlockBed = this.clone() as BlockBed
            blockBed.setHeadPiece(true)

            world.setBlock(directionLocation, blockBed)
            world.setBlock(placePosition, this)

            BlockEntity.create<BlockEntityBed>(BlockEntityType.BED, blockBed)?.let {
                it.setColor(blockColor)
                it.spawn()
            }

            BlockEntity.create<BlockEntityBed>(BlockEntityType.BED, this)?.let {
                it.setColor(blockColor)
                it.spawn()
            }
            return true
        }
        return false
    }

    override fun onBlockBreak(breakLocation: Vector) {
        var direction = getDirection()
        if (this.isHeadPiece()) {
            direction = direction.opposite()
        }
        val otherBlock: Block = this.getRelative(direction.toBlockFace())
        if (otherBlock is BlockBed) {
            if (otherBlock.isHeadPiece() != this.isHeadPiece()) {
                this.getWorld().setBlock(otherBlock.getLocation(), AIR)
            }
        }
        this.getWorld().setBlock(breakLocation, AIR)
    }

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.SOUTH
            1 -> Direction.WEST
            2 -> Direction.NORTH
            else -> Direction.EAST
        }
    }

    override fun setDirection(value: Direction): BlockBed {
        return when (value) {
            Direction.SOUTH -> this.setState("direction", 0)
            Direction.WEST -> this.setState("direction", 1)
            Direction.NORTH -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun isHeadPiece(): Boolean {
        return this.getBooleanState("head_piece_bit")
    }

    override fun setHeadPiece(value: Boolean): BlockBed {
        return this.setState("head_piece_bit", value.toByte())
    }

    override fun isOccupied(): Boolean {
        return this.getBooleanState("occupied_bit")
    }

    override fun setOccupied(value: Boolean): BlockBed {
        return this.setState("occupied_bit", value.toByte())
    }
}