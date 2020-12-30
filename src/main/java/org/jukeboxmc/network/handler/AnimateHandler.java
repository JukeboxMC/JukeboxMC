package org.jukeboxmc.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.jukeboxmc.network.packet.AnimatePacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnimateHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        AnimatePacket animatePacket = (AnimatePacket) packet;
        switch ( animatePacket.getAction() ) {
            case SWING_ARM:
                animatePacket.setBuffer( ByteBufAllocator.DEFAULT.buffer() );
                player.getServer().broadcastPacket( animatePacket );
                break;
            default:
                break;
        }
        //System.out.println( animatePacket.toString() );
    }
}
