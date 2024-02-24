package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Sponge
import org.jukeboxmc.api.block.data.SpongeType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSponge(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Sponge {

   override fun getSpongeType(): SpongeType {
       return SpongeType.valueOf(this.getStringState("sponge_type"))
   }

   override fun setSpongeType(value: SpongeType): Sponge {
       return this.setState("sponge_type", value.name.lowercase())
   }
}