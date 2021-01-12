package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        if(!player.isSpawned()) {
            player.getPlayerConnection().joinGame();
        }

        for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
            if ( onlinePlayer != null ) {
                if ( onlinePlayer != player ) {
                    onlinePlayer.getPlayerConnection().spawnPlayer( player );
                    player.getPlayerConnection().spawnPlayer( onlinePlayer );
                }
            }
        }
        player.getServer().broadcastMessage( "§e" + player.getName() + " has joined the game" );
    }
}
