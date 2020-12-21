package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.ContainerClosePacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ContainerCloseHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ContainerClosePacket containerClosePacket = (ContainerClosePacket) packet;

        ContainerClosePacket closePacket = new ContainerClosePacket();
        closePacket.setWindowId( containerClosePacket.getWindowId() );
        containerClosePacket.setServerInitiated( false );
        player.getPlayerConnection().sendPacket( closePacket );
    }
}
