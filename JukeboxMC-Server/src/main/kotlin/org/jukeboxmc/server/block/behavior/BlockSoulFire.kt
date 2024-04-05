package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SoulFire
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSoulFire(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SoulFire {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getAge(): Int {
        return this.getIntState("age")
    }

    override fun setAge(value: Int): SoulFire {
        return this.setState("age", value)
    }
}