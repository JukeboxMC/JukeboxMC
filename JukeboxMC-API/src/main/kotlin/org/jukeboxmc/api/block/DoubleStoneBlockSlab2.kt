package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StoneSlabType2
import org.jukeboxmc.api.block.data.VerticalHalf

interface DoubleStoneBlockSlab2 : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): DoubleStoneBlockSlab2

   fun getStoneSlabType2(): StoneSlabType2

   fun setStoneSlabType2(value: StoneSlabType2): DoubleStoneBlockSlab2

}