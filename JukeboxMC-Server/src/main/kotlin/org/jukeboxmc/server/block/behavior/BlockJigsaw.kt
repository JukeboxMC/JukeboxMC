package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Jigsaw
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.block.JukeboxBlock

class BlockJigsaw(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Jigsaw {

   override fun getFacingDirection(): BlockFace {
       return BlockFace.entries[this.getIntState("facing_direction")]
   }

   override fun setFacingDirection(value: BlockFace): Jigsaw {
       return this.setState("facing_direction", value.ordinal)
   }

   override fun getRotation(): Int {
       return this.getIntState("rotation")
   }

   override fun setRotation(value: Int): Jigsaw {
       return this.setState("rotation", value)
   }
}