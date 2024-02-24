package org.jukeboxmc.api.block

interface CopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): CopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): CopperBulb

}