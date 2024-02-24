package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket
import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.network.BedrockServer
import org.jukeboxmc.server.player.JukeboxPlayer

class RequestNetworkSettingsHandler : PacketHandler<RequestNetworkSettingsPacket> {

    override fun handle(packet: RequestNetworkSettingsPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (packet.protocolVersion != BedrockServer.BEDROCK_CODEC.protocolVersion) {
            val playStatusPacket = PlayStatusPacket()
            if (packet.protocolVersion > BedrockServer.BEDROCK_CODEC.protocolVersion) {
                playStatusPacket.status = PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD
            } else {
                playStatusPacket.status = PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD
            }
            player.sendPacketImmediately(playStatusPacket)
            return
        }

        val networkSettingsPacket = NetworkSettingsPacket()
        networkSettingsPacket.clientThrottleThreshold = 0
        networkSettingsPacket.compressionAlgorithm = server.getCompressionAlgorithm()
        player.sendPacketImmediately(networkSettingsPacket)

        //Idk why but it works
        JukeboxMC.getServer().getScheduler().scheduleDelayed({
            player.getSession().setCompression(networkSettingsPacket.compressionAlgorithm)
        }, 1)
    }
}