package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.HardStainedGlassPane
import org.jukeboxmc.api.block.data.Color
import org.jukeboxmc.server.block.JukeboxBlock

class BlockHardStainedGlassPane(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), HardStainedGlassPane {

   override fun getColor(): Color {
       return Color.valueOf(this.getStringState("color"))
   }

   override fun setColor(value: Color): HardStainedGlassPane {
       return this.setState("color", value.name.lowercase())
   }
}