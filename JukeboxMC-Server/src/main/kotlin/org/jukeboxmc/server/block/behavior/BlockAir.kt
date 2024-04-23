package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.server.block.JukeboxBlock

class BlockAir(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return true
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun canBeFlowedInto(): Boolean {
        return true
    }
}