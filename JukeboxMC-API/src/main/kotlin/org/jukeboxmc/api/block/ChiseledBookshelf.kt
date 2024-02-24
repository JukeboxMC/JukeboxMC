package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface ChiseledBookshelf : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): ChiseledBookshelf

   fun getBooksStored(): Int

   fun setBooksStored(value: Int): ChiseledBookshelf

}