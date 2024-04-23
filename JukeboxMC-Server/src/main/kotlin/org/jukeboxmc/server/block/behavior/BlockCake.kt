package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Cake
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.Difficulty
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCake(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Cake {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        if (this.getRelative(BlockFace.DOWN).getType() == BlockType.AIR) {
            return false
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
        if (player.isHungry() || player.getGameMode() == GameMode.CREATIVE || player.getWorld()
                .getDifficulty() == Difficulty.PEACEFUL
        ) {
            var biteCounter: Int = this.getCounter()
            if (biteCounter < 6) {
                this.setCounter(biteCounter + 1.also { biteCounter = it })
            }
            if (biteCounter >= 6) {
                player.getWorld().setBlock(this.getLocation(), AIR)
            } else {
                player.addHunger(2)
                player.addSaturation(0.4f)
            }
            return true
        }
        return false
    }

    override fun getCounter(): Int {
        return this.getIntState("bite_counter")
    }

    override fun setCounter(value: Int): BlockCake {
        return this.setState("bite_counter", value)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}