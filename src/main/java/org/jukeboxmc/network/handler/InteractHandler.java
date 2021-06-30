package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.InteractPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InteractHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        InteractPacket interactPacket = (InteractPacket) packet;

        if ( interactPacket.getAction() == InteractPacket.Action.OPEN_INVENTORY ) {
            player.openInventory( player.getInventory() );
        }
    }
}