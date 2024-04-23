package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CopperBulb
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockCopperBulb(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CopperBulb {

    override fun isLit(): Boolean {
        return this.getBooleanState("lit")
    }

    override fun setLit(value: Boolean): BlockCopperBulb {
        return this.setState("lit", value.toByte())
    }

    override fun isPowered(): Boolean {
        return this.getBooleanState("powered_bit")
    }

    override fun setPowered(value: Boolean): BlockCopperBulb {
        return this.setState("powered_bit", value.toByte())
    }
}