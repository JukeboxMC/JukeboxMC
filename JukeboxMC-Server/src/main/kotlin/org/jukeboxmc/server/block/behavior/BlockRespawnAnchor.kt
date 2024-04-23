package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.RespawnAnchor
import org.jukeboxmc.server.block.JukeboxBlock

class BlockRespawnAnchor(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    RespawnAnchor {

    override fun getRespawnAnchorCharge(): Int {
        return this.getIntState("respawn_anchor_charge")
    }

    override fun setRespawnAnchorCharge(value: Int): RespawnAnchor {
        return this.setState("respawn_anchor_charge", value)
    }
}