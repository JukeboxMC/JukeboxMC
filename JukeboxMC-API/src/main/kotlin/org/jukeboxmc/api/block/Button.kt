package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Button : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Button

   fun isButtonPressed(): Boolean

   fun setButtonPressed(value: Boolean): Button

}