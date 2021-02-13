package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerJoinEvent;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        player.getPlayerConnection().spawnToAll();

        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent( player, "§e" + player.getName() + " has joined the game" );
        Server.getInstance().getPluginManager().callEvent( playerJoinEvent );
        if ( playerJoinEvent.getJoinMessage() != null && !playerJoinEvent.getJoinMessage().isEmpty() ) {
            Server.getInstance().broadcastMessage( playerJoinEvent.getJoinMessage() );
        }
    }
}
