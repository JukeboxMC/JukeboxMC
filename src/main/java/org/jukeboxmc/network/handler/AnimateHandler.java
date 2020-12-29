package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.AnimatePacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnimateHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        AnimatePacket animatePacket = (AnimatePacket) packet;

        //System.out.println( animatePacket.toString() );
    }
}
