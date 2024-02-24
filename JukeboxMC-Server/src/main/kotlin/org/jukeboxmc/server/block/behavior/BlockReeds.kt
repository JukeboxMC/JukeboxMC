package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Reeds
import org.jukeboxmc.server.block.JukeboxBlock

class BlockReeds(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Reeds {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getAge(): Int {
       return this.getIntState("age")
   }

   override fun setAge(value: Int): Reeds {
       return this.setState("age", value)
   }
}