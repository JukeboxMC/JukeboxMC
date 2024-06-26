package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.server.block.JukeboxBlock

class BlockAzalea(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getWaterLoggingLevel(): Int {
        return 1
    }
}