package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.LightBlock
import org.jukeboxmc.server.block.JukeboxBlock

class BlockLightBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), LightBlock {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getBlockLightLevel(): Int {
       return this.getIntState("block_light_level")
   }

   override fun setBlockLightLevel(value: Int): LightBlock {
       return this.setState("block_light_level", value)
   }
}