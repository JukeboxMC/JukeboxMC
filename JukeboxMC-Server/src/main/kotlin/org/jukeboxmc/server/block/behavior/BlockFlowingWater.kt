package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FlowingWater
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFlowingWater(identifier: Identifier, blockStates: NbtMap?) : BlockLiquid(identifier, blockStates), FlowingWater {

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockFlowingWater) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }
}