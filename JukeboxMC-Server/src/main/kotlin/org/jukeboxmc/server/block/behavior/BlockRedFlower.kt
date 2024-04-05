package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.RedFlower
import org.jukeboxmc.api.block.data.FlowerType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockRedFlower(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), RedFlower {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getFlowerType(): FlowerType {
        return FlowerType.valueOf(this.getStringState("flower_type"))
    }

    override fun setFlowerType(value: FlowerType): RedFlower {
        return this.setState("flower_type", value.name.lowercase())
    }
}