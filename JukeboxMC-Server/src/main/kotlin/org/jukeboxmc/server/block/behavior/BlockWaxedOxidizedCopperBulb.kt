package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.WaxedOxidizedCopperBulb
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockWaxedOxidizedCopperBulb(identifier: Identifier, blockStates: NbtMap?) :
    JukeboxBlock(identifier, blockStates), WaxedOxidizedCopperBulb {

    override fun isLit(): Boolean {
        return this.getBooleanState("lit")
    }

    override fun setLit(value: Boolean): WaxedOxidizedCopperBulb {
        return this.setState("lit", value.toByte())
    }

    override fun isPowered(): Boolean {
        return this.getBooleanState("powered_bit")
    }

    override fun setPowered(value: Boolean): WaxedOxidizedCopperBulb {
        return this.setState("powered_bit", value.toByte())
    }
}