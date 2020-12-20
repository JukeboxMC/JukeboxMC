package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.TextPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TextHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        TextPacket textPacket = (TextPacket) packet;

        if ( textPacket.getType() == TextPacket.Type.CHAT ) {
            for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
                onlinePlayer.sendMessage( "<" + player.getName() + "> " + textPacket.getMessage() );
            }
        }
    }
}
