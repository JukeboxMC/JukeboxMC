package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.FlowingLava
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFlowingLava(identifier: Identifier, blockStates: NbtMap?) : BlockLava(identifier, blockStates), FlowingLava {

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockFlowingLava) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }
}