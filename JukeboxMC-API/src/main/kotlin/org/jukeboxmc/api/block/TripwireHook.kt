package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface TripwireHook : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): TripwireHook

   fun isAttached(): Boolean

   fun setAttached(value: Boolean): TripwireHook

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): TripwireHook

}