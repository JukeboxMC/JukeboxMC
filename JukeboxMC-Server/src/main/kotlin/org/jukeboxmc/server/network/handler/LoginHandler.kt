package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket
import org.cloudburstmc.protocol.bedrock.packet.ServerToClientHandshakePacket
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils
import org.jukeboxmc.api.event.player.PlayerLoginEvent
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.network.LoginSequenceUtil
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.player.LoginData

class LoginHandler : PacketHandler<LoginPacket> {

    override fun handle(packet: LoginPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.setLoginData(LoginData().fromLoginPacket(packet))
        val playerLoginEvent = PlayerLoginEvent(player)

        if (!player.getLoginData()?.xboxAuthenticated!! && server.isOnlineMode()) {
            playerLoginEvent.setResult(PlayerLoginEvent.Result.XBOX_AUTHENTICATED)
            playerLoginEvent.setKickReason("You must be logged in with your xbox account.")
        }

        if (server.getOnlinePlayers().size >= server.getMaxPlayers()) {
            playerLoginEvent.setResult(PlayerLoginEvent.Result.SERVER_FULL)
            playerLoginEvent.setKickReason("Server is full.")
        }

        if (server.isWhitelist() && !server.getWhitelist().isWhitelisted(player.getName())) {
            playerLoginEvent.setResult(PlayerLoginEvent.Result.WHITELIST)
            playerLoginEvent.setKickReason("You are not on the whitelist")
        }

        server.getPluginManager().callEvent(playerLoginEvent)
        if (playerLoginEvent.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            DisconnectPacket().apply {
                this.reason = DisconnectFailReason.KICKED
                this.kickMessage = playerLoginEvent.getKickReason()
                player.sendPacket(this)
            }
            return
        }

        if (server.isEncrypted()) {
            val token = EncryptionUtils.generateRandomToken()
            val serverToClientHandshakePacket = ServerToClientHandshakePacket()
            serverToClientHandshakePacket.jwt = EncryptionUtils.createHandshakeJwt(server.getKeyPair(), token)

            val session = player.getSession()
            val secretKey = EncryptionUtils.getSecretKey(server.getKeyPair().private, player.getLoginData()?.identityPublicKey!!, token)

            session.peer.channel.eventLoop().execute {
                session.sendPacketImmediately(serverToClientHandshakePacket)
                session.enableEncryption(secretKey)
            }
        } else {
            LoginSequenceUtil.processLoginSequence(server, player)
        }
    }
}