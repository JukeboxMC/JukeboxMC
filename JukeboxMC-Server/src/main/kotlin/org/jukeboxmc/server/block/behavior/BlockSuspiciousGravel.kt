package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SuspiciousGravel
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockSuspiciousGravel(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SuspiciousGravel {

   override fun isHanging(): Boolean {
       return this.getBooleanState("hanging")
   }

   override fun setHanging(value: Boolean): SuspiciousGravel {
       return this.setState("hanging", value.toByte())
   }

   override fun getBrushedProgress(): Int {
       return this.getIntState("brushed_progress")
   }

   override fun setBrushedProgress(value: Int): SuspiciousGravel {
       return this.setState("brushed_progress", value)
   }
}