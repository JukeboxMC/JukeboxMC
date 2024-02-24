package org.jukeboxmc.api.block

interface ExposedCopperBulb : Block {

   fun isLit(): Boolean

   fun setLit(value: Boolean): ExposedCopperBulb

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): ExposedCopperBulb

}