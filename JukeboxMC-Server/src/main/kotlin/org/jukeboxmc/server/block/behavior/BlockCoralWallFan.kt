package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CoralWallFan
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCoralWallFan(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CoralWallFan {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getCoralDirection(): Direction {
        return Direction.entries[this.getIntState("coral_direction")]
    }

    override fun setCoralDirection(value: Direction): BlockCoralWallFan {
        return this.setState("coral_direction", value.ordinal)
    }
}