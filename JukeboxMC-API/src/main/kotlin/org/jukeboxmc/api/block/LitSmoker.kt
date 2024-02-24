package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface LitSmoker : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): LitSmoker

}