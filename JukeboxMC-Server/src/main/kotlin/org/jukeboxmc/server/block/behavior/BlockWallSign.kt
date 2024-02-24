package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.WallSign
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.block.JukeboxBlock

class BlockWallSign(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), WallSign {

   override fun getFacingDirection(): BlockFace {
       return BlockFace.entries[this.getIntState("facing_direction")]
   }

   override fun setFacingDirection(value: BlockFace): WallSign {
       return this.setState("facing_direction", value.ordinal)
   }
}