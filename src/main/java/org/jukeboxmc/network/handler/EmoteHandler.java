package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;

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
