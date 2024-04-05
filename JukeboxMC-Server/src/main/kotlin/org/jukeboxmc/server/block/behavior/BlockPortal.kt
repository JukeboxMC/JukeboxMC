package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Portal
import org.jukeboxmc.api.block.data.PortalAxis
import org.jukeboxmc.server.block.JukeboxBlock

class BlockPortal(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Portal {

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun getPortalAxis(): PortalAxis {
        return PortalAxis.valueOf(this.getStringState("portal_axis"))
    }

    override fun setPortalAxis(value: PortalAxis): Portal {
        return this.setState("portal_axis", value.name.lowercase())
    }
}