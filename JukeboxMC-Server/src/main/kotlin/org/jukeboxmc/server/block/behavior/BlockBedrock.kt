package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Bedrock
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockBedrock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Bedrock {

    override fun isInfiniburn(): Boolean {
        return this.getBooleanState("infiniburn_bit")
    }

    override fun setInfiniburn(value: Boolean): BlockBedrock {
        return this.setState("infiniburn_bit", value.toByte())
    }
}