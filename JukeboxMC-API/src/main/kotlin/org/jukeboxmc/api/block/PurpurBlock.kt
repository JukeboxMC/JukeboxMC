package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.ChiselType

interface PurpurBlock : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): PurpurBlock

   fun getChiselType(): ChiselType

   fun setChiselType(value: ChiselType): PurpurBlock

}