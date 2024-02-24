package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.RailDirection

interface Rail : Block {

   fun getRailDirection(): RailDirection

   fun setRailDirection(value: RailDirection): Rail

}