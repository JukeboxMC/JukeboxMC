package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BigDripleaf
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BigDripleafTilt
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.isOneOf
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockBigDripleaf(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BigDripleaf {

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

        if (!blockDown.getType().isOneOf(
                BlockType.BIG_DRIPLEAF,
                BlockType.GRASS_BLOCK,
                BlockType.DIRT,
                BlockType.DIRT_WITH_ROOTS,
                BlockType.MYCELIUM,
                BlockType.PODZOL,
                BlockType.MOSS_BLOCK,
                BlockType.CLAY
            )
        ) {
            return false
        }

        if (blockDown is BlockBigDripleaf) {
            val block = Block.create(BlockType.BIG_DRIPLEAF) as BlockBigDripleaf
            val direction = blockDown.getCardinalDirection()
            block.setCardinalDirection(direction)
            block.setBigDripleafHead(true)
            world.setBlock(placePosition, 0, player.getDimension(), block, false)
            this.setCardinalDirection(direction)
            this.setBigDripleafHead(true)
        } else {
            this.setCardinalDirection(player.getDirection().opposite())
            this.setBigDripleafHead(true)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun isBigDripleafHead(): Boolean {
        return this.getBooleanState("big_dripleaf_head")
    }

    override fun setBigDripleafHead(value: Boolean): BlockBigDripleaf {
        return this.setState("big_dripleaf_head", value.toByte())
    }

    override fun getBigDripleafTilt(): BigDripleafTilt {
        return BigDripleafTilt.valueOf(this.getStringState("big_dripleaf_tilt"))
    }

    override fun setBigDripleafTilt(value: BigDripleafTilt): BlockBigDripleaf {
        return this.setState("big_dripleaf_tilt", value.name.lowercase())
    }

    override fun getCardinalDirection(): Direction {
        return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
    }

    override fun setCardinalDirection(value: Direction): BigDripleaf {
        return this.setState("minecraft:cardinal_direction", value.name.lowercase())
    }

}