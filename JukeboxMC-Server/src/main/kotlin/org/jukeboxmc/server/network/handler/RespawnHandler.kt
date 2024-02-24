package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.RespawnPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.player.JukeboxPlayer

class RespawnHandler : PacketHandler<RespawnPacket> {

    override fun handle(packet: RespawnPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.state == RespawnPacket.State.CLIENT_READY) {
            if (player.getRespawnLocation() == null) return
            val respawnPositionPacket = RespawnPacket()
            respawnPositionPacket.runtimeEntityId = player.getEntityId()
            respawnPositionPacket.state = RespawnPacket.State.SERVER_READY
            respawnPositionPacket.position = player.getRespawnLocation()!!.toVector3f()
            player.sendPacket(respawnPositionPacket)
        }
    }
}