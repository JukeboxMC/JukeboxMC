package org.jukeboxmc.api.block

interface WeatheredCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): WeatheredCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): WeatheredCopperBulb

}