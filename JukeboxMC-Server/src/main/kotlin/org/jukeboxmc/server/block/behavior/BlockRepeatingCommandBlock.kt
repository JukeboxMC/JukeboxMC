package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.RepeatingCommandBlock
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockRepeatingCommandBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    RepeatingCommandBlock {

    override fun getFacingDirection(): BlockFace {
        return BlockFace.entries[this.getIntState("facing_direction")]
    }

    override fun setFacingDirection(value: BlockFace): RepeatingCommandBlock {
        return this.setState("facing_direction", value.ordinal)
    }

    override fun isConditional(): Boolean {
        return this.getBooleanState("conditional_bit")
    }

    override fun setConditional(value: Boolean): RepeatingCommandBlock {
        return this.setState("conditional_bit", value.toByte())
    }
}