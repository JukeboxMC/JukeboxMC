package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface PoweredComparator : Block {

   fun isOutputSubtract(): Boolean

   fun setOutputSubtract(value: Boolean): PoweredComparator

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): PoweredComparator

   fun isOutputLit(): Boolean

   fun setOutputLit(value: Boolean): PoweredComparator

}