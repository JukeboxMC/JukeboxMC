package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Candle
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCandle(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Candle {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val clickedBlock = world.getBlock(blockPosition)
        if (clickedBlock is BlockCandle) {
            return if (clickedBlock.getIdentifier() == itemInHand.getIdentifier()) {
                val candles = clickedBlock.getCandles()
                if (candles < 3) {
                    clickedBlock.setCandles(candles + 1)
                    world.setBlock(blockPosition, clickedBlock)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        } else {
            if (this.getIdentifier() == itemInHand.getIdentifier()) {
                val block = world.getBlock(placePosition)
                if (block.getType() == BlockType.AIR) {
                    return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
                }
                block as BlockCandle
                val candles = block.getCandles()
                return if (candles < 3) {
                    block.setCandles(candles + 1)
                    world.setBlock(placePosition, block)
                    true
                } else {
                    false
                }
            }
        }
        return false
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        if (this.getType().isCandle()) return false
        if (this.isLit() && itemInHand.getType() != ItemType.FLINT_AND_STEEL) {
            this.setLit(false)
            world.playLevelSound(blockPosition, SoundEvent.FIZZ)
            return true
        } else if (!this.isLit() && itemInHand.getType() == ItemType.FLINT_AND_STEEL) {
            this.setLit(true)
            world.playLevelSound(blockPosition, SoundEvent.IGNITE)
            return true
        }
        return false
    }

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return block.getType() == this.getType()
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun isLit(): Boolean {
        return this.getBooleanState("lit")
    }

    override fun setLit(value: Boolean): BlockCandle {
        return this.setState("lit", value.toByte())
    }

    override fun getCandles(): Int {
        return this.getIntState("candles")
    }

    override fun setCandles(value: Int): BlockCandle {
        return this.setState("candles", value)
    }
}