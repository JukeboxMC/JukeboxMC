package org.jukeboxmc.player;

import org.jukeboxmc.Server;
import org.jukeboxmc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerChunkManager {

    private static final long[] CHUNK_KEYS;

    static {
        Server server = Server.getInstance();
        int radius = server.getViewDistance();

        CHUNK_KEYS = new long[( radius + radius + 1 ) * ( radius + radius + 1 ) + 1];
        int arrayIndex = 0;
        for ( int chunkX = -radius; chunkX <= radius; chunkX++ ) {
            for ( int chunkZ = -radius; chunkZ <= radius; chunkZ++ ) {
                CHUNK_KEYS[arrayIndex++] = Utils.toLong( chunkX, chunkZ );
            }
        }
    }

    public static List<Long> forPlayer( int centerX, int centerZ, int viewDistance ) {
        List<Long> chunks = new ArrayList<>();
        for ( long chunkKey : CHUNK_KEYS ) {
            int sendXChunk = Utils.fromHashX( chunkKey );
            int sendZChunk = Utils.fromHashZ( chunkKey );
            double distance = Math.sqrt( sendZChunk * sendZChunk + sendXChunk * sendXChunk );
            long chunkDistance = Math.round( distance );

            if ( chunkDistance <= viewDistance ) {
                chunks.add( Utils.toLong( sendXChunk + centerX, sendZChunk + centerZ ) );
            }
        }

        return chunks;
    }

}
