package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Wheat
import org.jukeboxmc.server.block.JukeboxBlock

class BlockWheat(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Wheat {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): Wheat {
       return this.setState("growth", value)
   }
}