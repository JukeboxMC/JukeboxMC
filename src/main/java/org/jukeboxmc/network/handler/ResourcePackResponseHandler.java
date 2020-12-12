package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.ResourcePackResponsePacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackResponseHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ResourcePackResponsePacket resourcePackResponsePacket = (ResourcePackResponsePacket) packet;

    }
}
