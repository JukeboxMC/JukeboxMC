package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.AzaleaLeaves
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockAzaleaLeaves(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    AzaleaLeaves {

    override fun isUpdate(): Boolean {
        return this.getBooleanState("update_bit")
    }

    override fun setUpdate(value: Boolean): BlockAzaleaLeaves {
        return this.setState("update_bit", value.toByte())
    }

    override fun isPersistent(): Boolean {
        return this.getBooleanState("persistent_bit")
    }

    override fun setPersistent(value: Boolean): BlockAzaleaLeaves {
        return this.setState("persistent_bit", value.toByte())
    }
}