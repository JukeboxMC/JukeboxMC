package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Lectern : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): Lectern

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): Lectern

}