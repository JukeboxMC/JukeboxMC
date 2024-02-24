package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.HardStainedGlass
import org.jukeboxmc.api.block.data.Color
import org.jukeboxmc.server.block.JukeboxBlock

class BlockHardStainedGlass(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), HardStainedGlass {

   override fun getColor(): Color {
       return Color.valueOf(this.getStringState("color"))
   }

   override fun setColor(value: Color): HardStainedGlass {
       return this.setState("color", value.name.lowercase())
   }
}