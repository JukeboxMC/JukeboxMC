package org.jukeboxmc.api.block

interface WaxedWeatheredCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): WaxedWeatheredCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): WaxedWeatheredCopperBulb

}