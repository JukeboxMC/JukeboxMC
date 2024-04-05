package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CaveVinesBodyWithBerries
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCaveVinesBodyWithBerries(identifier: Identifier, blockStates: NbtMap?) :
    JukeboxBlock(identifier, blockStates),
    CaveVinesBodyWithBerries {

    override fun getGrowingPlantAge(): Int {
        return this.getIntState("growing_plant_age")
    }

    override fun setGrowingPlantAge(value: Int): BlockCaveVinesBodyWithBerries {
        return this.setState("growing_plant_age", value)
    }
}