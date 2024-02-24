package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.InteractPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class InteractHandler : PacketHandler<InteractPacket> {

    override fun handle(packet: InteractPacket, server: JukeboxServer, player: JukeboxPlayer) {
       if (packet.action == InteractPacket.Action.OPEN_INVENTORY) {
           player.openInventory(player.getInventory())
       }
    }
}