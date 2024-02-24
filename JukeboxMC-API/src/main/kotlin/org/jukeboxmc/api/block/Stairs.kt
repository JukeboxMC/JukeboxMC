package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.WeirdoDirection

interface Stairs : Block {

   fun isUpsideDown(): Boolean

   fun setUpsideDown(value: Boolean): Stairs

   fun getWeirdoDirection(): WeirdoDirection

   fun setWeirdoDirection(value: WeirdoDirection): Stairs

}