package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.DoubleStoneBlockSlab4
import org.jukeboxmc.api.block.data.StoneSlabType4
import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDoubleStoneBlockSlab4(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoubleStoneBlockSlab4 {

   override fun getVerticalHalf(): VerticalHalf {
       return VerticalHalf.valueOf(this.getStringState("minecraft:vertical_half"))
   }

   override fun setVerticalHalf(value: VerticalHalf): BlockDoubleStoneBlockSlab4 {
       return this.setState("minecraft:vertical_half", value.name.lowercase())
   }

   override fun getStoneSlabType4(): StoneSlabType4 {
       return StoneSlabType4.valueOf(this.getStringState("stone_slab_type_4"))
   }

   override fun setStoneSlabType4(value: StoneSlabType4): BlockDoubleStoneBlockSlab4 {
       return this.setState("stone_slab_type_4", value.name.lowercase())
   }
}