package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.DoubleSlab
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleSlab {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleSlab {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }
}