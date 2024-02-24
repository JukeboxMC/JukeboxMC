package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SeaPickle
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockSeaPickle(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SeaPickle {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isDead(): Boolean {
       return this.getBooleanState("dead_bit")
   }

   override fun setDead(value: Boolean): SeaPickle {
       return this.setState("dead_bit", value.toByte())
   }

   override fun getClusterCount(): Int {
       return this.getIntState("cluster_count")
   }

   override fun setClusterCount(value: Int): SeaPickle {
       return this.setState("cluster_count", value)
   }
}