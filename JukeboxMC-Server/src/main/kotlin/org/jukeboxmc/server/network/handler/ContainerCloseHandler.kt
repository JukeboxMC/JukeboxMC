package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.ContainerClosePacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class ContainerCloseHandler : PacketHandler<ContainerClosePacket> {

    override fun handle(packet: ContainerClosePacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.closeInventory(packet.id, packet.isServerInitiated)
    }
}