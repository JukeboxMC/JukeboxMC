package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.ResourcePackResponsePacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackResponseHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ResourcePackResponsePacket resourcePackResponsePacket = (ResourcePackResponsePacket) packet;
        ResourcePackResponsePacket.Status status = resourcePackResponsePacket.getResponseStatus();

        if ( status == ResourcePackResponsePacket.Status.STATUS_REFUSED ) {
            //Disconnect
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_SEND_PACKS ) {

        } else if ( status == ResourcePackResponsePacket.Status.STATUS_HAVE_ALL_PACKS ) {
           player.getPlayerConnection().sendResourcePackStack();
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_COMPLETED ) {

        }

        System.out.println( status.name() );

    }
}
