package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CoralFanHang2
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockCoralFanHang2(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CoralFanHang2 {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getCoralDirection(): Direction {
       return Direction.entries[this.getIntState("coral_direction")]
   }

   override fun setCoralDirection(value: Direction): BlockCoralFanHang2 {
       return this.setState("coral_direction", value.ordinal)
   }

   override fun isCoralHangType(): Boolean {
       return this.getBooleanState("coral_hang_type_bit")
   }

   override fun setCoralHangType(value: Boolean): BlockCoralFanHang2 {
       return this.setState("coral_hang_type_bit", value.toByte())
   }

   override fun isDead(): Boolean {
       return this.getBooleanState("dead_bit")
   }

   override fun setDead(value: Boolean): BlockCoralFanHang2 {
       return this.setState("dead_bit", value.toByte())
   }
}