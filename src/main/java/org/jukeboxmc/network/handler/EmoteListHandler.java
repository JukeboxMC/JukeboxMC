package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.EmoteListPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EmoteListHandler implements PacketHandler<EmoteListPacket> {

    @Override
    public void handle( EmoteListPacket packet, Server server, Player player ) {
        player.getEmotes().addAll( packet.getEmotes() );
    }
}
