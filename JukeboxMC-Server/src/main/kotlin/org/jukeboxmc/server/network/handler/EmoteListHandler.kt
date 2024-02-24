package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.EmoteListPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class EmoteListHandler : PacketHandler<EmoteListPacket> {

    override fun handle(packet: EmoteListPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.setEmotes(packet.pieceIds)
    }
}