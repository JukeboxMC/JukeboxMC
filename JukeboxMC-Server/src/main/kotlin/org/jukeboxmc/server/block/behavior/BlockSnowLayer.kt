package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.SnowLayer
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSnowLayer(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SnowLayer {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        var block = world.getBlock(blockPosition)
        val blockDown = world.getBlock(placePosition).getRelative(BlockFace.DOWN)
        if (!blockDown.isSolid() && blockDown.getType() != BlockType.SNOW_LAYER) return false

        if (block is BlockSnowLayer) {
            if (block.getHeight() != 7) {
                block.setHeight(block.getHeight() + 1)
                world.setBlock(blockPosition, block)
            } else {
                block = world.getBlock(placePosition)
                if (block is BlockSnowLayer) {
                    if (block.getHeight() != 7) {
                        block.setHeight(block.getHeight() + 1)
                        world.setBlock(placePosition, block)
                    } else {
                        this.setHeight(0)
                        world.setBlock(placePosition, this)
                    }
                } else {
                    this.setHeight(0)
                    world.setBlock(placePosition, this)
                }
            }
        } else {
            this.setHeight(0)
            world.setBlock(placePosition, this)
        }
        return true
    }

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return this.getHeight() < 7
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun isCovered(): Boolean {
        return this.getBooleanState("covered_bit")
    }

    override fun setCovered(value: Boolean): SnowLayer {
        return this.setState("covered_bit", value.toByte())
    }

    override fun getHeight(): Int {
        return this.getIntState("height")
    }

    override fun setHeight(value: Int): SnowLayer {
        return this.setState("height", value)
    }
}