package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.EmoteFlag;
import com.nukkitx.protocol.bedrock.packet.EmotePacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author pooooooon
 * @version 1.0
 */
public class EmoteHandler implements PacketHandler<EmotePacket>{

    @Override
    public void handle(@NotNull EmotePacket packet, @NotNull Server server, @NotNull Player player ) {
        if ( packet.getRuntimeEntityId() != player.getEntityId() ) {
            return;
        }
        packet.getFlags().add( EmoteFlag.SERVER_SIDE );
        server.broadcastPacket( packet );
    }
}
