package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.AnimatePacket;
import org.jukeboxmc.player.Player;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnimateHandler implements PacketHandler<AnimatePacket> {

    @Override
    public void handle( AnimatePacket packet, Server server, Player player ) {
        switch ( packet.getAction() ) {
            case SWING_ARM:
                Set<Player> players = player.getServer().getOnlinePlayers().stream().filter( p -> p != player ).collect( Collectors.toSet() );
                if ( !players.isEmpty() )
                    player.getServer().broadcastPacket( players, packet );
                break;
            default:
                break;
        }
    }
}
