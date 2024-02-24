package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Axis

interface BoneBlock : Block {

   fun getPillarAxis(): Axis

   fun setPillarAxis(value: Axis): BoneBlock

   fun getDeprecated(): Int

   fun setDeprecated(value: Int): BoneBlock

}