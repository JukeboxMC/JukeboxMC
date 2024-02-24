package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface WallBanner : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): WallBanner

}