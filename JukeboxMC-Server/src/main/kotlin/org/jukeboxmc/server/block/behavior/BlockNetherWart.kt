package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.NetherWart
import org.jukeboxmc.server.block.JukeboxBlock

class BlockNetherWart(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), NetherWart {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getAge(): Int {
       return this.getIntState("age")
   }

   override fun setAge(value: Int): NetherWart {
       return this.setState("age", value)
   }
}