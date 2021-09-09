package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.TextPacket;
import org.jukeboxmc.network.packet.type.TextType;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TextHandler implements PacketHandler<TextPacket>{

    @Override
    public void handle( TextPacket packet, Server server, Player player ) {
        if ( packet.getType() == TextType.CHAT ) {
            for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
                onlinePlayer.sendMessage( "<" + player.getName() + "> " + packet.getMessage() );
            }
            server.getLogger().info( "<" + player.getName() + "> " + packet.getMessage() );
        }
    }
}
