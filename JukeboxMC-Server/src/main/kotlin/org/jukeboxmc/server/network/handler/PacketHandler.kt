package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer


interface PacketHandler<T : BedrockPacket> {

    fun handle(packet: T, server: JukeboxServer, player: JukeboxPlayer)
}


