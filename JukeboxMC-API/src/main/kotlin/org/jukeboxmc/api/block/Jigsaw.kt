package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface Jigsaw : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): Jigsaw

   fun getRotation(): Int

   fun setRotation(value: Int): Jigsaw

}