package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.RailDirection

interface DetectorRail : Block {

   fun getRailDirection(): RailDirection

   fun setRailDirection(value: RailDirection): DetectorRail

   fun isRailData(): Boolean

   fun setRailData(value: Boolean): DetectorRail

}