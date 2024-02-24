package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Campfire : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): Campfire

   fun isExtinguished(): Boolean

   fun setExtinguished(value: Boolean): Campfire

}