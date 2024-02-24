package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Beetroot
import org.jukeboxmc.server.block.JukeboxBlock

class BlockBeetroot(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Beetroot {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getGrowth(): Int {
       return this.getIntState("growth")
   }

   override fun setGrowth(value: Int): BlockBeetroot {
       return this.setState("growth", value)
   }
}