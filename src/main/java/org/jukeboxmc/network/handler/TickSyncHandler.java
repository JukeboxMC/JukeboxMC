package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.TickSyncPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TickSyncHandler implements PacketHandler<TickSyncPacket>{

    @Override
    public void handle( TickSyncPacket packet, Server server, Player player ) {
        TickSyncPacket tickSyncPacket = new TickSyncPacket();
        tickSyncPacket.setResponseTimestamp( packet.getResponseTimestamp() );
        player.getPlayerConnection().sendPacket( tickSyncPacket );
    }
}
