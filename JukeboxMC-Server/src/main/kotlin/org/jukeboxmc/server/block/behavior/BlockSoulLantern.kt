package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SoulLantern
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockSoulLantern(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SoulLantern {

   override fun isHanging(): Boolean {
       return this.getBooleanState("hanging")
   }

   override fun setHanging(value: Boolean): SoulLantern {
       return this.setState("hanging", value.
       toByte())
   }
}