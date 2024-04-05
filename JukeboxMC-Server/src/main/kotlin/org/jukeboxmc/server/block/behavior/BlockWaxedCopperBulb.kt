package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.WaxedCopperBulb
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockWaxedCopperBulb(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    WaxedCopperBulb {

    override fun isLit(): Boolean {
        return this.getBooleanState("lit")
    }

    override fun setLit(value: Boolean): WaxedCopperBulb {
        return this.setState("lit", value.toByte())
    }

    override fun isPowered(): Boolean {
        return this.getBooleanState("powered_bit")
    }

    override fun setPowered(value: Boolean): WaxedCopperBulb {
        return this.setState("powered_bit", value.toByte())
    }
}