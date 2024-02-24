package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface CherryWood : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): CherryWood

   fun isStripped(): Boolean

   fun setStripped(value: Boolean): CherryWood

}