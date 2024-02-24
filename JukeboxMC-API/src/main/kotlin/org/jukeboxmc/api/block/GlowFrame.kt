package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface GlowFrame : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): GlowFrame

   fun isItemFrameMap(): Boolean

   fun setItemFrameMap(value: Boolean): GlowFrame

   fun isItemFramePhoto(): Boolean

   fun setItemFramePhoto(value: Boolean): GlowFrame

}