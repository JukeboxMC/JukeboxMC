package org.jukeboxmc.api.block

interface OxidizedCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): OxidizedCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): OxidizedCopperBulb

}