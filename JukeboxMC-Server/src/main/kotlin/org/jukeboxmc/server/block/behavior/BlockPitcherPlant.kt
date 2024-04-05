package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PitcherPlant
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockPitcherPlant(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    PitcherPlant {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun isUpperBlock(): Boolean {
        return this.getBooleanState("upper_block_bit")
    }

    override fun setUpperBlock(value: Boolean): PitcherPlant {
        return this.setState("upper_block_bit", value.toByte())
    }
}