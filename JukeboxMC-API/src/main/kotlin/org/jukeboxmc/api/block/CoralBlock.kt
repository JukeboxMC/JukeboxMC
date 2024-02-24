package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CoralColor

interface CoralBlock : Block {

   fun getCoralColor(): CoralColor

   fun setCoralColor(value: CoralColor): CoralBlock

   fun isDead(): Boolean

   fun setDead(value: Boolean): CoralBlock

}