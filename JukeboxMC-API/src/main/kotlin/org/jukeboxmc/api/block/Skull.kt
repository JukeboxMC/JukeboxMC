package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Skull : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Skull

}