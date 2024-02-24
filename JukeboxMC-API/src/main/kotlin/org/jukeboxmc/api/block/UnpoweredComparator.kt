package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface UnpoweredComparator : Block {

   fun isOutputSubtract(): Boolean

   fun setOutputSubtract(value: Boolean): UnpoweredComparator

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): UnpoweredComparator

   fun isOutputLit(): Boolean

   fun setOutputLit(value: Boolean): UnpoweredComparator

}