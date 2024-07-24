package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.VerticalHalf

interface DoubleStoneBlockSlab : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): DoubleStoneBlockSlab

}