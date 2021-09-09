package jukeboxmc.network.handler;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.RequestChunkRadiusPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler<RequestChunkRadiusPacket> {

    @Override
    public void handle( RequestChunkRadiusPacket packet, Server server, Player player ) {
        player.setViewDistance( FastMath.min( packet.getRadius(), player.getServer().getViewDistance() ) );
    }
}
