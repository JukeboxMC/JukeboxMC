package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface ChainCommandBlock : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): ChainCommandBlock

   fun isConditional(): Boolean

   fun setConditional(value: Boolean): ChainCommandBlock

}