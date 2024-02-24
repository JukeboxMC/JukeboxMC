package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface MelonStem : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): MelonStem

   fun getGrowth(): Int

   fun setGrowth(value: Int): MelonStem

}