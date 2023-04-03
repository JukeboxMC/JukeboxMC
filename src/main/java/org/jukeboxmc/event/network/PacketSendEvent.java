package org.jukeboxmc.event.network;

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.event.Event;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketSendEvent extends Event implements Cancellable {

    private final Player player;
    private BedrockPacket packet;

    public PacketSendEvent( Player player, BedrockPacket packet ) {
        this.player = player;
        this.packet = packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public BedrockPacket getPacket() {
        return this.packet;
    }

    public void setPacket( BedrockPacket packet ) {
        this.packet = packet;
    }
}
