package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class LevelSoundEventHandler : PacketHandler<LevelSoundEventPacket> {

    override fun handle(packet: LevelSoundEventPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.getWorld().sendChunkPacket(player.getChunkX(), player.getChunkZ(), packet)
    }
}