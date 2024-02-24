package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Barrel : Block {

   fun isOpen(): Boolean

   fun setOpen(value: Boolean): Barrel

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Barrel

}