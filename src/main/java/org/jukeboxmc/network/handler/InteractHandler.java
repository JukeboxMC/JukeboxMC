package org.jukeboxmc.network.handler;

import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.network.packet.ContainerOpenPacket;
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
        System.out.println( interactPacket.toString() );

        if ( interactPacket.getAction() == InteractPacket.Action.OPEN_INVENTORY ) {
            ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
            containerOpenPacket.setWindowId( WindowId.PLAYER );
            containerOpenPacket.setWindowType( player.getInventory().getWindowType() );
            containerOpenPacket.setPosition( player.getLocation() );
            containerOpenPacket.setEntityId( player.getEntityId() );
            player.getPlayerConnection().sendPacket( containerOpenPacket );
        }
    }
}
