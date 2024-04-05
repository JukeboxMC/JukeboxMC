package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.StructureVoid
import org.jukeboxmc.api.block.data.StructureVoidType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockStructureVoid(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    StructureVoid {

    override fun getStructureVoidType(): StructureVoidType {
        return StructureVoidType.valueOf(this.getStringState("structure_void_type"))
    }

    override fun setStructureVoidType(value: StructureVoidType): StructureVoid {
        return this.setState("structure_void_type", value.name.lowercase())
    }
}