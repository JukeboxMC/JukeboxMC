package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface CoralFanHang3 : Block {

   fun getCoralDirection(): Direction

   fun setCoralDirection(value: Direction): CoralFanHang3

   fun isCoralHangType(): Boolean

   fun setCoralHangType(value: Boolean): CoralFanHang3

   fun isDead(): Boolean

   fun setDead(value: Boolean): CoralFanHang3

}