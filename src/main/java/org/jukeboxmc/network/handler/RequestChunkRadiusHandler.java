package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler<RequestChunkRadiusPacket> {

    @Override
    public void handle( RequestChunkRadiusPacket packet, Server server, Player player ) {
        player.setChunkRadius( packet.getRadius() );
    }
}
