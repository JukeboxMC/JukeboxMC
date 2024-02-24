package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Kelp
import org.jukeboxmc.server.block.JukeboxBlock

class BlockKelp(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Kelp {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getKelpAge(): Int {
       return this.getIntState("kelp_age")
   }

   override fun setKelpAge(value: Int): Kelp {
       return this.setState("kelp_age", value)
   }
}