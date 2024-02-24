package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.ClientToServerHandshakePacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.network.LoginSequenceUtil
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class ClientToServerHandshakeHandler : PacketHandler<ClientToServerHandshakePacket> {

    override fun handle(packet: ClientToServerHandshakePacket, server: JukeboxServer, player: JukeboxPlayer) {
        LoginSequenceUtil.processLoginSequence(server, player)
    }
}