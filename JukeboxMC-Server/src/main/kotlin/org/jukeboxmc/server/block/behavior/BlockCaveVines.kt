package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CaveVines
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCaveVines(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CaveVines {

    override fun getGrowingPlantAge(): Int {
        return this.getIntState("growing_plant_age")
    }

    override fun setGrowingPlantAge(value: Int): BlockCaveVines {
        return this.setState("growing_plant_age", value)
    }
}