package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Kelp
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockKelp(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Kelp {

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
        if (block.getType() == BlockType.WATER) {
            val blockDown = block.getRelative(BlockFace.DOWN)
            if (blockDown.isSolid() || blockDown.getType() == BlockType.KELP) {
                world.setBlock(placePosition, this, 0)
                world.setBlock(placePosition, block, 1)
                return true
            }
        }
        return false
    }

    override fun onBlockBreak(breakLocation: Vector) {
        val world = getWorld()
        val block = world.getBlock(breakLocation, 1)
        if (block is BlockWater) {
            world.setBlock(breakLocation, block, 0)
            world.setBlock(breakLocation, AIR, 1)
            return
        }
        super.onBlockBreak(breakLocation)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getKelpAge(): Int {
        return this.getIntState("kelp_age")
    }

    override fun setKelpAge(value: Int): Kelp {
        return this.setState("kelp_age", value)
    }

    override fun getWaterLoggingLevel(): Int {
        return 2
    }
}