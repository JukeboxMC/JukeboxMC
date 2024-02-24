package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Sandstone
import org.jukeboxmc.api.block.data.SandStoneType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSandstone(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Sandstone {

   override fun getSandStoneType(): SandStoneType {
       return SandStoneType.valueOf(this.getStringState("sand_stone_type"))
   }

   override fun setSandStoneType(value: SandStoneType): Sandstone {
       return this.setState("sand_stone_type", value.name.lowercase())
   }
}