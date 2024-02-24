package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StoneSlabType3
import org.jukeboxmc.api.block.data.VerticalHalf

interface DoubleStoneBlockSlab3 : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): DoubleStoneBlockSlab3

   fun getStoneSlabType3(): StoneSlabType3

   fun setStoneSlabType3(value: StoneSlabType3): DoubleStoneBlockSlab3

}