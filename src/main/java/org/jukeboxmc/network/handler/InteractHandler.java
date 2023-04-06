package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.InteractPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InteractHandler implements PacketHandler<InteractPacket> {
    @Override
    public void handle( InteractPacket packet, Server server, Player player ) {
        if ( packet.getAction().equals( InteractPacket.Action.OPEN_INVENTORY ) ) {
            player.openInventory( player.getInventory() );
        }
    }
}
