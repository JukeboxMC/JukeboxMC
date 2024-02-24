package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface CommandBlock : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): CommandBlock

   fun isConditional(): Boolean

   fun setConditional(value: Boolean): CommandBlock

}