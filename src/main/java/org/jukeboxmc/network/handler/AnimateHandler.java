package org.jukeboxmc.network.handler;

import io.netty.buffer.ByteBufAllocator;
import org.jukeboxmc.network.packet.AnimatePacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

import java.util.Set;
import java.util.stream.Collectors;

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
                Set<Player> players = player.getServer().getOnlinePlayers().stream().filter( p -> p != player ).collect( Collectors.toSet() );
                if ( !players.isEmpty() )
                    player.getServer().broadcastPacket( players, animatePacket );
                break;
            default:
                break;
        }
    }
}
