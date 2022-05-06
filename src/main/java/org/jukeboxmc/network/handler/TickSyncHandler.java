package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.TickSyncPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TickSyncHandler implements PacketHandler<TickSyncPacket>{

    @Override
    public void handle( TickSyncPacket packet, Server server, Player player ) {
        TickSyncPacket tickSyncPacket = new TickSyncPacket();
        //tickSyncPacket.setResponseTimestamp( packet.getRequestTimestamp() );
        //System.out.println(packet);
    }
}
