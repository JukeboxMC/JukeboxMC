package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface StonecutterBlock : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): StonecutterBlock

}