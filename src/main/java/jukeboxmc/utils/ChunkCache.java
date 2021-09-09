package jukeboxmc.utils;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ChunkCache {

    private final World world;
    private final Long2ObjectMap<Chunk> cachedChunks;

    public ChunkCache( World world ) {
        this.world = world;
        this.cachedChunks = new Long2ObjectOpenHashMap<>( 8 * 8, Hash.VERY_FAST_LOAD_FACTOR );
    }

    public synchronized boolean putChunk( long hash, Chunk chunk ) {
        Chunk chunkAdapter = this.cachedChunks.get( hash );
        if ( chunkAdapter == null ) {
            this.cachedChunks.put( hash, chunk );
        }
        return chunkAdapter == chunk;
    }

    public synchronized Chunk getChunk( int chunkX, int chunkZ ) {
        return this.cachedChunks.get( Utils.toLong( chunkX, chunkZ ) );
    }
}
