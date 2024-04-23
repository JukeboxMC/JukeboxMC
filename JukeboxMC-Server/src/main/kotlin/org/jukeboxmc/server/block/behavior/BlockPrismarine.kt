package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Prismarine
import org.jukeboxmc.api.block.data.PrismarineType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPrismarine(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Prismarine {

    override fun getPrismarineBlockType(): PrismarineType {
        return PrismarineType.valueOf(this.getStringState("prismarine_block_type"))
    }

    override fun setPrismarineBlockType(value: PrismarineType): Prismarine {
        return this.setState("prismarine_block_type", value.name.lowercase())
    }
}