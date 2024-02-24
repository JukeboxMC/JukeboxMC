package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.TextPacket
import org.jukeboxmc.api.event.player.PlayerChatEvent
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class TextHandler : PacketHandler<TextPacket> {

    override fun handle(packet: TextPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.type == TextPacket.Type.CHAT) {
            val playerChatEvent = PlayerChatEvent(player, "<" + player.getName() + "> ", packet.message)
            server.getPluginManager().callEvent(playerChatEvent)
            if (playerChatEvent.isCancelled()) {
                return
            }
            for (onlinePlayer in player.getServer().getOnlinePlayers()) {
                onlinePlayer.sendMessage(playerChatEvent.getFormat() + playerChatEvent.getMessage())
            }
            server.getLogger().info(playerChatEvent.getFormat() + playerChatEvent.getMessage())
        }
    }
}