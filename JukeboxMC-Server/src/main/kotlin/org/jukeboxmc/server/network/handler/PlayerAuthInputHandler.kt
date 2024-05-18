package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.EncodingSettings
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatBlockBreakingModule
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatMovementModule
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class PlayerAuthInputHandler : PacketHandler<PlayerAuthInputPacket> {

    override fun handle(packet: PlayerAuthInputPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (!player.isSpawned()) {
            return
        }

        server.getAntiCheatRegistry().getModule(AntiCheatMovementModule::class.java).handle(player, packet, server)
        server.getAntiCheatRegistry().getModule(AntiCheatBlockBreakingModule::class.java).handle(player, packet, server)

        if (packet.itemStackRequest != null) {
            (HandlerRegistry.getPacketHandler(ItemStackRequestPacket::class.java)!! as ItemStackRequestHandler).handleItemStackRequest(packet.itemStackRequest, player, mutableListOf())
        }
    }
}