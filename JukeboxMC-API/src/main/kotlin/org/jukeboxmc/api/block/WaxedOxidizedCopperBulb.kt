package org.jukeboxmc.api.block

interface WaxedOxidizedCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): WaxedOxidizedCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): WaxedOxidizedCopperBulb

}