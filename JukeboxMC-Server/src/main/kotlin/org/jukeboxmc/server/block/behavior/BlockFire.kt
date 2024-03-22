package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Fire
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFire(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Fire {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getAge(): Int {
       return this.getIntState("age")
   }

   override fun setAge(value: Int): Fire {
       return this.setState("age", value)
   }

    override fun isCollidable(): Boolean {
        return false
    }

}