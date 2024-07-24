package org.jukeboxmc.server.network

import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class LoginSequenceUtil {

    companion object {
        fun processLoginSequence(server: JukeboxServer, player: JukeboxPlayer) {
            val playStatusPacket = PlayStatusPacket()
            playStatusPacket.status = PlayStatusPacket.Status.LOGIN_SUCCESS
            player.sendPacketImmediately(playStatusPacket)

            val resourcePacksInfoPacket = ResourcePacksInfoPacket()
            server.getResourcePackManager().getResourcePacks().forEach {
                resourcePacksInfoPacket.resourcePackInfos.add(
                    ResourcePacksInfoPacket.Entry(
                        it.getUuid().toString(),
                        it.getVersion(),
                        it.getSize(),
                        "",
                        "",
                        it.getUuid().toString(),
                        false,
                        false,
                        false
                    ))
            }
            resourcePacksInfoPacket.isForcedToAccept = server.isForceResourcePacks()
            resourcePacksInfoPacket.isForcingServerPacksEnabled = false
            resourcePacksInfoPacket.isScriptingEnabled = false
            player.sendPacket(resourcePacksInfoPacket)
        }
    }
}