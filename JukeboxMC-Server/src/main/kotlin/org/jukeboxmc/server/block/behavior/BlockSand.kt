package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Sand
import org.jukeboxmc.api.block.data.SandType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSand(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Sand {

    override fun getSandType(): SandType {
        return SandType.valueOf(this.getStringState("sand_type"))
    }

    override fun setSandType(value: SandType): Sand {
        return this.setState("sand_type", value.name.lowercase())
    }
}