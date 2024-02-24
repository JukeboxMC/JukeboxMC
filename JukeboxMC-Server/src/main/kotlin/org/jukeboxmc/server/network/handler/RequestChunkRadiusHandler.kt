package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class RequestChunkRadiusHandler : PacketHandler<RequestChunkRadiusPacket> {

    override fun handle(packet: RequestChunkRadiusPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.getPlayerChunkManager().setChunkRadius(packet.radius)
    }
}