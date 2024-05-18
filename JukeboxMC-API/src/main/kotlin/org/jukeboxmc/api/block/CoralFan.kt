package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CoralFanDirection

interface CoralFan : Block {

   fun getCoralFanDirection(): CoralFanDirection

   fun setCoralFanDirection(value: CoralFanDirection): CoralFan

}