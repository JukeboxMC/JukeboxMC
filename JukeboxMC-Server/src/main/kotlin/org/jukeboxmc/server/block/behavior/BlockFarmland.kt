package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Farmland
import org.jukeboxmc.server.block.JukeboxBlock

class BlockFarmland(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Farmland {

    override fun getMoisturizedAmount(): Int {
        return this.getIntState("moisturized_amount")
    }

    override fun setMoisturizedAmount(value: Int): Farmland {
        return this.setState("moisturized_amount", value)
    }
}