package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface CoralFanHang2 : Block {

   fun getCoralDirection(): Direction

   fun setCoralDirection(value: Direction): CoralFanHang2

   fun isCoralHangType(): Boolean

   fun setCoralHangType(value: Boolean): CoralFanHang2

   fun isDead(): Boolean

   fun setDead(value: Boolean): CoralFanHang2

}