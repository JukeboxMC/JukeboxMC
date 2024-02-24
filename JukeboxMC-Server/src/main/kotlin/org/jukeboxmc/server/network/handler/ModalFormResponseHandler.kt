package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.ModalFormResponsePacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class ModalFormResponseHandler : PacketHandler<ModalFormResponsePacket> {

    override fun handle(packet: ModalFormResponsePacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.parseGUIResponse(packet.formId, packet.formData)
    }
}