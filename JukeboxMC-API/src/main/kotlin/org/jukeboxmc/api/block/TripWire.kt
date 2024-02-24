package org.jukeboxmc.api.block

interface TripWire : Block {

   fun isAttached(): Boolean

   fun setAttached(value: Boolean): TripWire

   fun isPowered(): Boolean

   fun setPowered(value: Boolean): TripWire

   fun isSuspended(): Boolean

   fun setSuspended(value: Boolean): TripWire

   fun isDisarmed(): Boolean

   fun setDisarmed(value: Boolean): TripWire

}