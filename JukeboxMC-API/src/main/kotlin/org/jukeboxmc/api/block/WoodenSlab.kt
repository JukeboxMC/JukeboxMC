package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.VerticalHalf
import org.jukeboxmc.api.block.data.WoodType

interface WoodenSlab : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): WoodenSlab

   fun getWoodType(): WoodType

   fun setWoodType(value: WoodType): WoodenSlab

}