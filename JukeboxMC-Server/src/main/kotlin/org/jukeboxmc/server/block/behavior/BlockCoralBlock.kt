package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CoralBlock
import org.jukeboxmc.api.block.data.CoralColor
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockCoralBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CoralBlock {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getCoralColor(): CoralColor {
       return CoralColor.valueOf(this.getStringState("coral_color"))
   }

   override fun setCoralColor(value: CoralColor): BlockCoralBlock {
       return this.setState("coral_color", value.name.lowercase())
   }

   override fun isDead(): Boolean {
       return this.getBooleanState("dead_bit")
   }

   override fun setDead(value: Boolean): BlockCoralBlock {
       return this.setState("dead_bit", value.toByte())
   }
}