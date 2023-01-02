package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler<RequestChunkRadiusPacket> {

    @Override
    public void handle( RequestChunkRadiusPacket packet, Server server, Player player ) {
        player.setChunkRadius( FastMath.min( packet.getRadius(), server.getViewDistance() ) );
    }
}
