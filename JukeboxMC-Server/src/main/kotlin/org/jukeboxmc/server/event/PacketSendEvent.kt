package org.jukeboxmc.server.event

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.event.Event
import org.jukeboxmc.server.player.JukeboxPlayer

class PacketSendEvent(
    private val player: JukeboxPlayer,
    private var bedrockPacket: BedrockPacket
) : Event(), Cancellable {

    fun getPlayer(): JukeboxPlayer {
        return this.player
    }

    fun getBedrockPacket(): BedrockPacket {
        return this.bedrockPacket
    }

    fun setBedrockPacket(bedrockPacket: BedrockPacket) {
        this.bedrockPacket = bedrockPacket
    }

}