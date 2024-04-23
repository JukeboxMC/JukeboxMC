package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.GoldenRail
import org.jukeboxmc.api.block.data.RailDirection
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockGoldenRail(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    GoldenRail {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getRailDirection(): RailDirection {
        return RailDirection.entries[this.getIntState("rail_direction")]
    }

    override fun setRailDirection(value: RailDirection): GoldenRail {
        return this.setState("rail_direction", value.ordinal)
    }

    override fun isRailData(): Boolean {
        return this.getBooleanState("rail_data_bit")
    }

    override fun setRailData(value: Boolean): GoldenRail {
        return this.setState("rail_data_bit", value.toByte())
    }
}