package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.DoubleWoodenSlab
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.block.data.WoodType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleWoodenSlab(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleWoodenSlab {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleWoodenSlab {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getWoodType(): WoodType {
       return WoodType.valueOf(this.getStringState("wood_type"))
   }

   override fun setWoodType(value: WoodType): BlockDoubleWoodenSlab {
       return this.setState("wood_type", value.name.lowercase())
   }
}