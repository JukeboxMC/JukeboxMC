package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FlowingWater
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFlowingWater(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), FlowingWater {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getLiquidDepth(): Int {
       return this.getIntState("liquid_depth")
   }

   override fun setLiquidDepth(value: Int): FlowingWater {
       return this.setState("liquid_depth", value)
   }

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockFlowingWater) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }
}