package org.jukeboxmc.server.world.chunk.serializer

import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.palette.RuntimeDataSerializer

class NetworkIdSerializer : RuntimeDataSerializer<JukeboxBlock> {
    override fun serialize(value: JukeboxBlock): Int {
        return value.getNetworkId()
    }
}
