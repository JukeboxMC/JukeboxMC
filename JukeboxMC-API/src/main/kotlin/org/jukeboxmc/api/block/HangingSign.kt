package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.SignDirection

interface HangingSign : Block {

   fun getGroundSignDirection(): SignDirection

   fun setGroundSignDirection(value: SignDirection): HangingSign

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): HangingSign

   fun isAttached(): Boolean

   fun setAttached(value: Boolean): HangingSign

   fun isHanging(): Boolean

   fun setHanging(value: Boolean): HangingSign

}