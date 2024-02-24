package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Bed : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Bed

   fun isHeadPiece(): Boolean

   fun setHeadPiece(value: Boolean): Bed

   fun isOccupied(): Boolean

   fun setOccupied(value: Boolean): Bed

}