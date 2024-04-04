package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Liquid
import org.jukeboxmc.api.block.Water
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

open class BlockWater(identifier: Identifier, blockStates: NbtMap?) : BlockLiquid(identifier, blockStates), Water {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        world.setBlock(placePosition.getBlockX(), placePosition.getBlockY(), placePosition.getBlockZ(), 0, placePosition.getDimension(), this, true)
        return true
    }

    override fun getLiquidWithNewDepth(depth: Int): Liquid {
        return (blockWater.clone() as BlockWater).setLiquidDepth(depth)
    }

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockWater) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }

    override fun tickRate(): Long {
        return 5
    }
}