package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.cloudburstmc.nbt.NbtType
import org.jukeboxmc.api.block.data.Color
import org.jukeboxmc.api.blockentity.BlockEntityBanner
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntityBanner(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntityBanner {

    private var baseColor: Int = 0
    private var type: Int = 0
    private val patterns: MutableList<NbtMap> = mutableListOf()

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        this.baseColor = compound.getInt("Base", 0)
        this.type = compound.getInt("Type", 0)

        val patterns = compound.getList("Patterns", NbtType.COMPOUND)
        for (pattern in patterns) {
            this.patterns.add(pattern)
        }
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        compound.putInt("Base", this.baseColor)
        compound.putInt("Type", this.type)
        if (this.patterns.isNotEmpty()) {
            compound.putList("Patterns", NbtType.COMPOUND, this.patterns)
        }
        return compound
    }

    override fun getColor(): Color {
        return Color.entries[this.baseColor]
    }

    override fun setColor(color: Color) {
        this.baseColor = color.ordinal
    }

    override fun getType(): Int {
        return this.type
    }

    override fun setType(type: Int) {
        this.type = type
    }

    override fun getPattern(): MutableList<NbtMap> {
        return this.patterns
    }
}