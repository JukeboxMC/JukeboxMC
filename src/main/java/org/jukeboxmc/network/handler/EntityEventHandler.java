package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityEventHandler implements PacketHandler<EntityEventPacket>{

    @Override
    public void handle(@NotNull EntityEventPacket packet, @NotNull Server server, @NotNull Player player ) {
        if ( packet.getType().equals( EntityEventType.EATING_ITEM ) ) {
            if ( packet.getData() == 0 || packet.getRuntimeEntityId() != player.getEntityId() ) {
                return;
            }
            server.broadcastPacket( packet );
        }
    }
}

