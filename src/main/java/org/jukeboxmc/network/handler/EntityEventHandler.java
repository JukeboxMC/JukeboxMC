package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.EntityEventType;
import org.jukeboxmc.network.packet.EntityEventPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityEventHandler implements PacketHandler<EntityEventPacket> {

    @Override
    public void handle( EntityEventPacket packet, Server server, Player player ) {
        if ( packet.getEntityEventType().equals( EntityEventType.EATING_ITEM ) ) {
            if ( packet.getData() == 0 || packet.getEntityId() != player.getEntityId() ) {
                return;
            }
            server.broadcastPacket( packet );
        }
    }
}
