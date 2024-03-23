package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.CoralFanDirection

interface CoralFanDead : Block {

   fun getCoralFanDirection(): CoralFanDirection

   fun setCoralFanDirection(value: CoralFanDirection): CoralFanDead

}