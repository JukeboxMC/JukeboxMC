package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.ChorusFlower
import org.jukeboxmc.server.block.JukeboxBlock

class BlockChorusFlower(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    ChorusFlower {

    override fun getAge(): Int {
        return this.getIntState("age")
    }

    override fun setAge(value: Int): BlockChorusFlower {
        return this.setState("age", value)
    }
}