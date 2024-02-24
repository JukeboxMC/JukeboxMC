package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.block.data.WoodType

interface DoubleWoodenSlab : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): DoubleWoodenSlab

   fun getWoodType(): WoodType

   fun setWoodType(value: WoodType): DoubleWoodenSlab

}