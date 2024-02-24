package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.BlockFace

interface PumpkinStem : Block {

   fun getFacingDirection(): BlockFace

   fun setFacingDirection(value: BlockFace): PumpkinStem

   fun getGrowth(): Int

   fun setGrowth(value: Int): PumpkinStem

}