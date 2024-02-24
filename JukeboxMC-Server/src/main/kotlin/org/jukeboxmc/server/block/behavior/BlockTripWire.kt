package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TripWire
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockTripWire(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), TripWire {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isAttached(): Boolean {
       return this.getBooleanState("attached_bit")
   }

   override fun setAttached(value: Boolean): TripWire {
       return this.setState("attached_bit", value.toByte())
   }

   override fun isPowered(): Boolean {
       return this.getBooleanState("powered_bit")
   }

   override fun setPowered(value: Boolean): TripWire {
       return this.setState("powered_bit", value.toByte())
   }

   override fun isSuspended(): Boolean {
       return this.getBooleanState("suspended_bit")
   }

   override fun setSuspended(value: Boolean): TripWire {
       return this.setState("suspended_bit", value.toByte())
   }

   override fun isDisarmed(): Boolean {
       return this.getBooleanState("disarmed_bit")
   }

   override fun setDisarmed(value: Boolean): TripWire {
       return this.setState("disarmed_bit", value.toByte())
   }
}