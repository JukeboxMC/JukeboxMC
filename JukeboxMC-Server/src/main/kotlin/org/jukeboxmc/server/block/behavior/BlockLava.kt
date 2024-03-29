package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Lava
import org.jukeboxmc.server.block.JukeboxBlock

class BlockLava(identifier: Identifier, blockStates: NbtMap?) : BlockLiquid(identifier, blockStates), Lava {

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockLava) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }
}