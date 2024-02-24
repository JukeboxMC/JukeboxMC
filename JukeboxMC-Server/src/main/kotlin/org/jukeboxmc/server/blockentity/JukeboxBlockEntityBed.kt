package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.jukeboxmc.api.block.data.Color
import org.jukeboxmc.api.blockentity.BlockEntityBed
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityBed(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityBed {

    private var color: Byte = 0

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        this.color = compound.getByte("color")
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        compound.putByte("color", this.color)
        return compound
    }

    override fun getColor(): Color {
        return Color.entries[color.toInt()]
    }

    override fun setColor(color: Color) {
        this.color = color.ordinal.toByte()
    }

}