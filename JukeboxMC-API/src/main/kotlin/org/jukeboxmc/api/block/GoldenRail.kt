package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.RailDirection

interface GoldenRail : Block {

   fun getRailDirection(): RailDirection

   fun setRailDirection(value: RailDirection): GoldenRail

   fun isRailData(): Boolean

   fun setRailData(value: Boolean): GoldenRail

}