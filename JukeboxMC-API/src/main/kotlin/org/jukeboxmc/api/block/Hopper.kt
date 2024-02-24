package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Hopper : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Hopper

   fun isToggle(): Boolean

   fun setToggle(value: Boolean): Hopper

}