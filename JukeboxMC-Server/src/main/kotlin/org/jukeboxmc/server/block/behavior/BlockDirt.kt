package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Dirt
import org.jukeboxmc.api.block.data.DirtType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockDirt(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Dirt {

    override fun getDirtType(): DirtType {
       return DirtType.valueOf(this.getStringState("dirt_type"))
   }

   override fun setDirtType(value: DirtType): BlockDirt {
       return this.setState("dirt_type", value.name.lowercase())
   }
}