package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;


/**
 * @author pooooooon
 * @version 1.0
 */
public class EmoteHandler implements PacketHandler<EmotePacket>{

    @Override
    public void handle( EmotePacket packet, Server server, Player player ) {
        if ( packet.getRuntimeEntityId() != player.getEntityId() ) {
            return;
        }
        packet.getFlags().add( EmoteFlag.SERVER_SIDE );
        server.broadcastPacket( packet );
    }
}
