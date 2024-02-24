package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface Chain : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): Chain

}