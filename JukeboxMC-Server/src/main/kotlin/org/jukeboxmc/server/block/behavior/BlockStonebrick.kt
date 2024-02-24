package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Stonebrick
import org.jukeboxmc.api.block.data.StoneBrickType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockStonebrick(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Stonebrick {

   override fun getStoneBrickType(): StoneBrickType {
       return StoneBrickType.valueOf(this.getStringState("stone_brick_type"))
   }

   override fun setStoneBrickType(value: StoneBrickType): Stonebrick {
       return this.setState("stone_brick_type", value.name.lowercase())
   }
}