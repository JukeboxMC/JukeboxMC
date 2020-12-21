package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.EmoteListPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EmoteListHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        EmoteListPacket emoteListPacket = (EmoteListPacket) packet;
        player.getEmotes().addAll( emoteListPacket.getEmotes() );
    }
}
