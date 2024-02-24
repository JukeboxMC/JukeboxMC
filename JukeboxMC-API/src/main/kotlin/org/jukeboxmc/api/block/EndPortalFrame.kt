package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface EndPortalFrame : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): EndPortalFrame

   fun isEndPortalEye(): Boolean

   fun setEndPortalEye(value: Boolean): EndPortalFrame

}