package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.VerticalHalf

interface Slab : Block {

   fun getVerticalHalf(): VerticalHalf

   fun setVerticalHalf(value: VerticalHalf): Slab

}