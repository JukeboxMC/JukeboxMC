package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket
import org.jukeboxmc.api.player.skin.Skin
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.fromNetwork
import org.jukeboxmc.server.player.JukeboxPlayer

class PlayerSkinHandler : PacketHandler<PlayerSkinPacket> {

    override fun handle(packet: PlayerSkinPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.setSkin(Skin().fromNetwork(packet.skin))
    }
}