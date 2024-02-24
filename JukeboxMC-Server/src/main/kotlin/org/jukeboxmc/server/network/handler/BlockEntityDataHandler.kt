package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.blockentity.JukeboxBlockEntitySign
import org.jukeboxmc.server.extensions.fromVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

class BlockEntityDataHandler : PacketHandler<BlockEntityDataPacket> {

    override fun handle(packet: BlockEntityDataPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.getWorld().getBlockEntity(Vector().fromVector3i(packet.blockPosition))?.let {
            if (it is JukeboxBlockEntitySign) {
                it.update(packet.data, player)
            }
        }
    }
}