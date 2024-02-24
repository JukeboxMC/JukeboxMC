package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.stream.Collectors

class AnimateHandler : PacketHandler<AnimatePacket> {

    override fun handle(packet: AnimatePacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.action == AnimatePacket.Action.SWING_ARM) {
            val players: Set<JukeboxPlayer> = player.getServer().getOnlinePlayers().stream().filter { p -> p.getUUID() != player.getUUID() }.map { it.toJukeboxPlayer() }.collect(Collectors.toSet())
            if (players.isNotEmpty()) {
                players.forEach {
                    it.sendPacket(packet)
                }
            }
        } else {
            server.getLogger().warn("Unhandled animation action: " + packet.action.name)
        }
    }
}