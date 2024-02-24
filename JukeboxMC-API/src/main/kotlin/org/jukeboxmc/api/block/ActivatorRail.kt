package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.RailDirection

interface ActivatorRail : Block {

   fun getRailDirection(): RailDirection

   fun setRailDirection(value: RailDirection): ActivatorRail

   fun isRailData(): Boolean

   fun setRailData(value: Boolean): ActivatorRail

}