package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface RepeatingCommandBlock : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): RepeatingCommandBlock

   fun isConditional(): Boolean

   fun setConditional(value: Boolean): RepeatingCommandBlock

}