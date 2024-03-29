package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Lava
import org.jukeboxmc.api.block.Liquid
import org.jukeboxmc.server.block.JukeboxBlock

open class BlockLiquid(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Liquid {

    override fun getLiquidDepth(): Int {
        return this.getIntState("liquid_depth")
    }

    override fun setLiquidDepth(value: Int): Lava {
        return this.setState("liquid_depth", value)
    }

    override fun canBeFlowedInto(): Boolean {
        return true
    }

    override fun canPassThrough(): Boolean {
        return true
    }
}