package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CoralFan
import org.jukeboxmc.api.block.data.CoralColor
import org.jukeboxmc.api.block.data.CoralFanDirection
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCoralFan(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CoralFan {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getCoralColor(): CoralColor {
       return CoralColor.valueOf(this.getStringState("coral_color"))
   }

   override fun setCoralColor(value: CoralColor): BlockCoralFan {
       return this.setState("coral_color", value.name.lowercase())
   }

   override fun getCoralFanDirection(): CoralFanDirection {
       return CoralFanDirection.entries[this.getIntState("coral_fan_direction")]
   }

   override fun setCoralFanDirection(value: CoralFanDirection): BlockCoralFan {
       return this.setState("coral_fan_direction", value.ordinal)
   }
}