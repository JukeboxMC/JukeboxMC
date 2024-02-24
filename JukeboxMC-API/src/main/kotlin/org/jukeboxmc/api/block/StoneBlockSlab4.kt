package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.StoneSlabType4
import org.jukeboxmc.api.block.data.VerticalHalf

interface StoneBlockSlab4 : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab4

   fun getStoneSlabType4(): StoneSlabType4

   fun setStoneSlabType4(value: StoneSlabType4): StoneBlockSlab4

}