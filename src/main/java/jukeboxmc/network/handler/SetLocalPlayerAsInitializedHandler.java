package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerJoinEvent;
import org.jukeboxmc.network.packet.SetLocalPlayerAsInitializedPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler<SetLocalPlayerAsInitializedPacket> {

    @Override
    public void handle( SetLocalPlayerAsInitializedPacket packet, Server server, Player player ) {
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent( player, "§e" + player.getName() + " has joined the game" );
        Server.getInstance().getPluginManager().callEvent( playerJoinEvent );
        if ( playerJoinEvent.getJoinMessage() != null && !playerJoinEvent.getJoinMessage().isEmpty() ) {
            Server.getInstance().broadcastMessage( playerJoinEvent.getJoinMessage() );
        }

        for ( Player onlinePlayer : server.getOnlinePlayers() ) {
            if ( onlinePlayer != null && onlinePlayer.getDimension() == player.getDimension() ) {
                onlinePlayer.spawn( player );
                player.spawn( onlinePlayer );
            }
        }
    }
}
