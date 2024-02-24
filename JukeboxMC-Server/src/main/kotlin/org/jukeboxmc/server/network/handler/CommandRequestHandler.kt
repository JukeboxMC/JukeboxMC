package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.CommandRequestPacket
import org.jukeboxmc.api.event.player.PlayerCommandPreprocessEvent
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

class CommandRequestHandler : PacketHandler<CommandRequestPacket> {

    override fun handle(packet: CommandRequestPacket, server: JukeboxServer, player: JukeboxPlayer) {
        val commandIdentifier = packet.command.substring(1).split(" ").toTypedArray()[0]
        val playerCommandProcessEvent = PlayerCommandPreprocessEvent(player, commandIdentifier)
        server.getPluginManager().callEvent(playerCommandProcessEvent)
        if (playerCommandProcessEvent.isCancelled()) return
        server.getCommandManager().handleCommandInput(player, packet.command)
    }
}