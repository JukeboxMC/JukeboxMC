package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface SmallDripleafBlock : Block {

   fun isUpperBlock(): Boolean

   fun setUpperBlock(value: Boolean): SmallDripleafBlock

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): SmallDripleafBlock

}