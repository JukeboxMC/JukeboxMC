package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CoralColor
import org.jukeboxmc.api.block.data.CoralFanDirection

interface CoralFanDead : Block {

   fun getCoralColor(): CoralColor

   fun setCoralColor(value: CoralColor): CoralFanDead

   fun getCoralFanDirection(): CoralFanDirection

   fun setCoralFanDirection(value: CoralFanDirection): CoralFanDead

}