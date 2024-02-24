package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Frame : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Frame

   fun isItemFrameMap(): Boolean

   fun setItemFrameMap(value: Boolean): Frame

   fun isItemFramePhoto(): Boolean

   fun setItemFramePhoto(value: Boolean): Frame

}