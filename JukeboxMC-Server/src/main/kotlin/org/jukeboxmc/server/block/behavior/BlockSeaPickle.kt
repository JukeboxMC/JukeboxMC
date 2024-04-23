package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SeaPickle
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSeaPickle(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SeaPickle {

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
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun isDead(): Boolean {
        return this.getBooleanState("dead_bit")
    }

    override fun setDead(value: Boolean): SeaPickle {
        return this.setState("dead_bit", value.toByte())
    }

    override fun getClusterCount(): Int {
        return this.getIntState("cluster_count")
    }

    override fun setClusterCount(value: Int): SeaPickle {
        return this.setState("cluster_count", value)
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}