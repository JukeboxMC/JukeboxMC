package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CoralFanDead
import org.jukeboxmc.api.block.data.CoralFanDirection
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCoralFanDead(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CoralFanDead {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getCoralFanDirection(): CoralFanDirection {
        return CoralFanDirection.entries[this.getIntState("coral_fan_direction")]
    }

    override fun setCoralFanDirection(value: CoralFanDirection): BlockCoralFanDead {
        return this.setState("coral_fan_direction", value.ordinal)
    }
}