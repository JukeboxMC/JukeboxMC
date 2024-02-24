package org.jukeboxmc.api.block

interface WaxedExposedCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): WaxedExposedCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): WaxedExposedCopperBulb

}