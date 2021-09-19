package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.network.packet.SetLocalPlayerAsInitializedPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler<SetLocalPlayerAsInitializedPacket> {

    @Override
    public void handle( SetLocalPlayerAsInitializedPacket packet, Server server, Player player ) {
        for ( Entity entity : player.getWorld().getEntities() ) {
            if ( !( entity instanceof Player ) && !entity.isClosed() ) {
                entity.spawn( player );
            }
        }
    }
}
