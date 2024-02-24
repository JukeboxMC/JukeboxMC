package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface HayBlock : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): HayBlock

   fun getDeprecated(): Int

   fun setDeprecated(value: Int): HayBlock

}