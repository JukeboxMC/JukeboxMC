package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.ModalResponsePacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ModalResponseHandler implements PacketHandler<ModalResponsePacket> {

    @Override
    public void handle( ModalResponsePacket packet, Server server, Player player ) {
        player.parseGUIResponse( packet.getFormId(), packet.getJson() );
    }
}
