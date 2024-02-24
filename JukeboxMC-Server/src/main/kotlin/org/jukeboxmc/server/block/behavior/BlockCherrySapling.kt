package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CherrySapling
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockCherrySapling(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CherrySapling {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun hasAge(): Boolean {
        return this.getBooleanState("age_bit")
    }

    override fun setAge(value: Boolean): BlockCherrySapling {
        return this.setState("age_bit", value.toByte())
    }
}