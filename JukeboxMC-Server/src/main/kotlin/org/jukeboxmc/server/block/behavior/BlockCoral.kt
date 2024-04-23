package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.event.block.BlockFadeEvent
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import java.util.concurrent.ThreadLocalRandom

class BlockCoral(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (this.getRelative(BlockFace.DOWN).isSolid()) {
            val block = world.getBlock(placePosition)
            if (block is BlockWater && block.getLiquidDepth() == 0) {
                world.setBlock(placePosition, block, 1, false)
            }
            world.setBlock(placePosition, this)
            return true
        }
        return false
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        val world = this.getWorld()
        val location = this.getLocation()
        if (updateReason == UpdateReason.NORMAL) {
            val blockDown = this.getRelative(BlockFace.DOWN)
            if (!blockDown.isSolid()) {
                world.setBlock(location, Block.create(BlockType.AIR))
                world.sendLevelEvent(location, LevelEvent.PARTICLE_DESTROY_BLOCK, this.getNetworkId())
                playBreakSound()
            } else if (!this.isDead()) {
                world.scheduleBlockUpdate(this, 60L + ThreadLocalRandom.current().nextInt(40))
            }
            return -1
        } else if (updateReason == UpdateReason.SCHEDULED) {
            val block = world.getBlock(location, 1)
            if (!isDead() && block !is BlockWater && block.getType() != BlockType.FROSTED_ICE) {
                val blockCoral = super.clone() as BlockCoral
                blockCoral.setDead()
                val blockFadeEvent = BlockFadeEvent(this, blockCoral)
                JukeboxServer.getInstance().getPluginManager().callEvent(blockFadeEvent)
                if (!blockFadeEvent.isCancelled()) {
                    this.setDead()
                }
            }
        }
        return -1
    }

    override fun getWaterLoggingLevel(): Int {
        return 2
    }

    private fun isDead(): Boolean {
        return when (this.getType()) {
            BlockType.DEAD_FIRE_CORAL,
            BlockType.DEAD_TUBE_CORAL,
            BlockType.DEAD_HORN_CORAL,
            BlockType.DEAD_BUBBLE_CORAL,
            BlockType.DEAD_BRAIN_CORAL -> true

            else -> false
        }
    }

    private fun setDead() {
        val blockType = this.getType()
        val location = this.getLocation()
        val world = this.getWorld()
        when (blockType) {
            BlockType.FIRE_CORAL -> {
                world.setBlock(location, Block.create(BlockType.DEAD_FIRE_CORAL))
            }

            BlockType.TUBE_CORAL -> {
                world.setBlock(location, Block.create(BlockType.DEAD_TUBE_CORAL))
            }

            BlockType.HORN_CORAL -> {
                world.setBlock(location, Block.create(BlockType.DEAD_HORN_CORAL))
            }

            BlockType.BUBBLE_CORAL -> {
                world.setBlock(location, Block.create(BlockType.DEAD_BUBBLE_CORAL))
            }

            BlockType.BRAIN_CORAL -> {
                world.setBlock(location, Block.create(BlockType.DEAD_BRAIN_CORAL))
            }

            else -> {}
        }
    }

}