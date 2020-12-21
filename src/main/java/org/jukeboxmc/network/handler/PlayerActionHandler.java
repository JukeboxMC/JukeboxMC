package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerActionPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerActionHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerActionPacket playerActionPacket = (PlayerActionPacket) packet;

        System.out.println( playerActionPacket.toString() );
    }
}
