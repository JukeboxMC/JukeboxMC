package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.ChemistryTable
import org.jukeboxmc.api.block.data.ChemistryTableType
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.server.block.JukeboxBlock

class BlockChemistryTable(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    ChemistryTable {

    override fun getDirection(): Direction {
        return when (this.getIntState("direction")) {
            0 -> Direction.EAST
            1 -> Direction.SOUTH
            2 -> Direction.WEST
            else -> Direction.NORTH
        }
    }

    override fun setDirection(value: Direction): BlockChemistryTable {
        return when (value) {
            Direction.EAST -> this.setState("direction", 0)
            Direction.SOUTH -> this.setState("direction", 1)
            Direction.WEST -> this.setState("direction", 2)
            else -> this.setState("direction", 3)
        }
    }

    override fun getChemistryTableType(): ChemistryTableType {
        return ChemistryTableType.valueOf(this.getStringState("chemistry_table_type"))
    }

    override fun setChemistryTableType(value: ChemistryTableType): BlockChemistryTable {
        return this.setState("chemistry_table_type", value.name.lowercase())
    }
}