package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FrostedIce
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFrostedIce(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    FrostedIce {

    override fun getAge(): Int {
        return this.getIntState("age")
    }

    override fun setAge(value: Int): FrostedIce {
        return this.setState("age", value)
    }
}