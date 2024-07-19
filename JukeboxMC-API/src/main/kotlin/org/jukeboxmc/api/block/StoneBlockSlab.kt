package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.VerticalHalf

interface StoneBlockSlab : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): StoneBlockSlab

}