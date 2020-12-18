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
        player.setSpawned( true );

        /*
        for ( int sendChunkX = -16; sendChunkX <= 16; sendChunkX++ ) {
            for ( int sendChunkZ = -16; sendChunkZ <= 16; sendChunkZ++ ) {
                player.getPlayerConnection().sendChunk( new Chunk( sendChunkX, sendChunkZ ) );
            }
        }
         */
    }
}
