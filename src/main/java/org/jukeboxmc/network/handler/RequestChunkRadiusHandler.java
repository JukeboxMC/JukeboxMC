package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.RequestChunkRadiusPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        RequestChunkRadiusPacket chunkRadiusPacket = (RequestChunkRadiusPacket) packet;
        int radius = Math.min( chunkRadiusPacket.getRadius(), player.getServer().getViewDistance() );

        player.setViewDistance( radius );
        player.getPlayerConnection().needNewChunks( false );
        if(!player.isSpawned()) {
            player.getPlayerConnection().firstSpawn();
        }
    }
}
//