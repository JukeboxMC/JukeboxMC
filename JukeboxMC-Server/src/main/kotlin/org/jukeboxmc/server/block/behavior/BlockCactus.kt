package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Cactus
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCactus(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Cactus {

   override fun getAge(): Int {
       return this.getIntState("age")
   }

   override fun setAge(value: Int): BlockCactus {
       return this.setState("age", value)
   }
}