package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.StickyPiston
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.block.JukeboxBlock

class BlockStickyPiston(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), StickyPiston {

   override fun getFacingDirection(): BlockFace {
       return BlockFace.entries[this.getIntState("facing_direction")]
   }

   override fun setFacingDirection(value: BlockFace): StickyPiston {
       return this.setState("facing_direction", value.ordinal)
   }
}