package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType
import org.cloudburstmc.protocol.bedrock.packet.EntityEventPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class EntityEventHandler : PacketHandler<EntityEventPacket> {

    override fun handle(packet: EntityEventPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.type == EntityEventType.EATING_ITEM) {
            if (packet.data == 0 || packet.runtimeEntityId != player.getEntityId()) {
                return
            }
            server.broadcastPacket(packet)
        }
    }
}