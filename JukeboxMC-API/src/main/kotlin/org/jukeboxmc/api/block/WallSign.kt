package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface WallSign : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): WallSign

}