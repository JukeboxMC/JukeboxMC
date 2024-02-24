package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface MangroveWood : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): MangroveWood

   fun isStripped(): Boolean

   fun setStripped(value: Boolean): MangroveWood

}