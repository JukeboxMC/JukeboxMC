package org.jukeboxmc.world.chunk;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Dimension;

import java.util.Collection;

/**
 * @author LucGamesYT, geNAZt
 * @version 1.0
 */
public class ChunkCache {

    private final Object2ObjectMap<Dimension, Long2ObjectMap<Chunk>> cachedChunks;

    public ChunkCache() {
        this.cachedChunks = new Object2ObjectOpenHashMap<>( 8 * 8, Hash.VERY_FAST_LOAD_FACTOR );

        for ( Dimension dimension : Dimension.values() ) {
            this.cachedChunks.put( dimension, new Long2ObjectOpenHashMap<>() );
        }
    }

    public synchronized Chunk getChunk( int chunkX, int chunkZ, Dimension dimension ) {
        long chunkHash = Utils.toLong( chunkX, chunkZ );
        return this.cachedChunks.get( dimension ).get( chunkHash );
    }

    public synchronized void putChunk( Chunk chunk, Dimension dimension ) {
        long hash = Utils.toLong( chunk.getX(), chunk.getZ() );
        Long2ObjectMap<Chunk> dimensionChunks = this.cachedChunks.get( dimension );

        Chunk adapterChunk = dimensionChunks.get( hash );
        if ( adapterChunk == null ) {
            dimensionChunks.put( hash, chunk );
        }

    }

    public synchronized boolean isChunkLoaded( int chunkX, int chunkZ, Dimension dimension ) {
        return this.cachedChunks.get( dimension ).containsKey( Utils.toLong( chunkX, chunkZ ) );
    }

    public synchronized void clearChunks() {
        this.cachedChunks.forEach( ( dimension, chunkLong2ObjectMap ) -> {
            chunkLong2ObjectMap.clear();
        } );
    }

    public synchronized Collection<Chunk> getChunks( Dimension dimension ) {
        return this.cachedChunks.get( dimension ).values();
    }

    public Object2ObjectMap<Dimension, Long2ObjectMap<Chunk>> getCachedChunks() {
        return this.cachedChunks;
    }

}
