package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Rail
import org.jukeboxmc.api.block.data.RailDirection
import org.jukeboxmc.server.block.JukeboxBlock

class BlockRail(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Rail {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun getRailDirection(): RailDirection {
       return RailDirection.entries[this.getIntState("rail_direction")]
   }

   override fun setRailDirection(value: RailDirection): Rail {
       return this.setState("rail_direction", value.ordinal)
   }
}