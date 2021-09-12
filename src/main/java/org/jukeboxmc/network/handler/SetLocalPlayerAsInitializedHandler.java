package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.SetLocalPlayerAsInitializedPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler<SetLocalPlayerAsInitializedPacket> {

    @Override
    public void handle( SetLocalPlayerAsInitializedPacket packet, Server server, Player player ) {
        for ( Player onlinePlayer : server.getOnlinePlayers() ) {
            if ( onlinePlayer != null && onlinePlayer.getDimension() == player.getDimension() ) {
                onlinePlayer.spawn( player );
                player.spawn( onlinePlayer );
            }
        }
    }
}
