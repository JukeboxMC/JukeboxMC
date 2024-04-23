package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Lava
import org.jukeboxmc.api.block.Liquid
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlock

open class BlockLava(identifier: Identifier, blockStates: NbtMap?) : BlockLiquid(identifier, blockStates), Lava {

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return true
    }

    override fun tickRate(): Long {
        if (this.getLocation().getDimension() == Dimension.NETHER) {
            return 10
        }
        return 30
    }

    override fun getFlowDecayPerBlock(): Int {
        if (this.getLocation().getDimension() == Dimension.NETHER) {
            return 1
        }
        return 2
    }

    override fun getLiquidWithNewDepth(depth: Int): Liquid {
        return (blockLava.clone() as BlockLava).setLiquidDepth(depth)
    }

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockLava) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }

    override fun checkForHarden() {
        var colliding: Block? = null
        for (value in BlockFace.entries) {
            if (value == BlockFace.DOWN) continue
            val blockSide: Block = this.getRelative(value)
            if (blockSide is BlockWater) {
                colliding = blockSide
                break
            }
        }
        if (colliding != null) {
            if (this.getLiquidDepth() == 0) {
                this.liquidCollide(Block.create(BlockType.OBSIDIAN).toJukeboxBlock())
            } else if (this.getLiquidDepth() <= 4) {
                this.liquidCollide(Block.create(BlockType.COBBLESTONE).toJukeboxBlock())
            }
        }
    }

    override fun flowIntoBlock(block: JukeboxBlock, newFlowDecay: Int) {
        if (block is BlockWater) {
            block.liquidCollide(Block.create(BlockType.STONE).toJukeboxBlock())
        } else {
            super.flowIntoBlock(block, newFlowDecay)
        }
    }
}