package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CaveVinesHeadWithBerries
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCaveVinesHeadWithBerries(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CaveVinesHeadWithBerries {

   override fun getGrowingPlantAge(): Int {
       return this.getIntState("growing_plant_age")
   }

   override fun setGrowingPlantAge(value: Int): BlockCaveVinesHeadWithBerries {
       return this.setState("growing_plant_age", value)
   }
}