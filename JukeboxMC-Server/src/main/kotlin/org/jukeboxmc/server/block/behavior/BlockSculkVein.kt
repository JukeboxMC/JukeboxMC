package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SculkVein
import org.jukeboxmc.server.block.JukeboxBlock

class BlockSculkVein(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), SculkVein {

    override fun getMultiFaceDirections(): Int {
        return this.getIntState("multi_face_direction_bits")
    }

    override fun setMultiFaceDirections(value: Int): SculkVein {
        return this.setState("multi_face_direction_bits", value)
    }
}