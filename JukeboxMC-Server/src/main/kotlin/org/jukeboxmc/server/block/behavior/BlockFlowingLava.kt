package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FlowingLava
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFlowingLava(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), FlowingLava {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getLiquidDepth(): Int {
       return this.getIntState("liquid_depth")
   }

   override fun setLiquidDepth(value: Int): FlowingLava {
       return this.setState("liquid_depth", value)
   }
}