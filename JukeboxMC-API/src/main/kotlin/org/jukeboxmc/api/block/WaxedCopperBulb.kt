package org.jukeboxmc.api.block

interface WaxedCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): WaxedCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): WaxedCopperBulb

}