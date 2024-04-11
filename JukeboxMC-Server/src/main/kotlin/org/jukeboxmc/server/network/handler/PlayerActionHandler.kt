package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.PlayerActionType
import org.cloudburstmc.protocol.bedrock.packet.PlayerActionPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class PlayerActionHandler : PacketHandler<PlayerActionPacket> {

    override fun handle(packet: PlayerActionPacket, server: JukeboxServer, player: JukeboxPlayer) {
        when (packet.action) {
            PlayerActionType.DIMENSION_CHANGE_SUCCESS -> {
                val playerActionPacket = PlayerActionPacket()
                playerActionPacket.action = PlayerActionType.DIMENSION_CHANGE_SUCCESS
                player.sendPacket(playerActionPacket)
            }

            PlayerActionType.RESPAWN -> {
                player.respawn()
            }

            else -> {}
        }
    }
}