package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Water
import org.jukeboxmc.server.block.JukeboxBlock

class BlockWater(identifier: Identifier, blockStates: NbtMap?) : BlockLiquid(identifier, blockStates), Water {

    override fun canCollideCheck(block: JukeboxBlock, value: Boolean): Boolean {
        if (block is BlockWater) {
            return value && block.getLiquidDepth() == 0
        }
        return false
    }
}