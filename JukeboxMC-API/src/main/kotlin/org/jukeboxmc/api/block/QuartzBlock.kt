package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis
import org.jukeboxmc.api.block.data.ChiselType

interface QuartzBlock : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): QuartzBlock

   fun getChiselType(): ChiselType

   fun setChiselType(value: ChiselType): QuartzBlock

}