package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class MovePlayerHandler : PacketHandler<MovePlayerPacket> {

    override fun handle(packet: MovePlayerPacket, server: JukeboxServer, player: JukeboxPlayer) {

    }
}