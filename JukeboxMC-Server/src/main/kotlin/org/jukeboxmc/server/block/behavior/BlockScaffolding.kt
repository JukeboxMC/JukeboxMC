package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Scaffolding
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockScaffolding(identifier: Identifier, blockStates: NbtMap?) : FallableBlock(identifier, blockStates), Scaffolding {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val block = world.getBlock(blockPosition)
        val placeBlock = world.getBlock(placePosition)
        val blockDown = this.getRelative(BlockFace.DOWN)
        if(block is BlockLava || block is BlockFlowingLava) {
            return false
        }
        if (placeBlock.getType() != BlockType.SCAFFOLDING && blockDown.getType() != BlockType.SCAFFOLDING && blockDown.getType() != BlockType.AIR && !blockDown.isSolid()) {
            var value = false
            for (horizontal in BlockFace.getHorizontal()) {
                if (horizontal != blockFace) {
                    val relativeBlock = this.getRelative(horizontal)
                    if (relativeBlock.getType() == BlockType.SCAFFOLDING) {
                        value = true
                        break
                    }
                }
            }
            if (!value) return false
        }
        world.setBlock(placePosition, this)
        return true
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        val block = world.getBlock(blockPosition)
        if (block.getType() != BlockType.SCAFFOLDING) return false
        if (blockFace != BlockFace.UP) return false
        val vector = blockPosition.clone()
        val yAt = world.getHighestBlockYAt(blockPosition.getBlockX(), blockPosition.getBlockZ(), blockPosition.getDimension())
        vector.setY(yAt.toFloat() + 1)
        world.setBlock(vector, Block.create(BlockType.SCAFFOLDING))
        this.playPlaceSound(vector)
        return true
    }

   override fun getStability(): Int {
       return this.getIntState("stability")
   }

   override fun setStability(value: Int): Scaffolding {
       return this.setState("stability", value)
   }

   override fun isStabilityCheck(): Boolean {
       return this.getBooleanState("stability_check")
   }

   override fun setStabilityCheck(value: Boolean): Scaffolding {
       return this.setState("stability_check", value.toByte())
   }
}