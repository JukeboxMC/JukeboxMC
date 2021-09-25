package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.network.packet.EntityFallPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityFallHandler implements PacketHandler<EntityFallPacket>{

    @Override
    public void handle( EntityFallPacket packet, Server server, Player player ) {
        Entity entity = player.getWorld().getEntity( packet.getEntityId() );
        if ( entity != null ) {
            entity.setFallDistance( packet.getFallDistance() );
        }
    }
}
