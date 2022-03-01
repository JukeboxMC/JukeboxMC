package org.jukeboxmc.world.chunk;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.iq80.leveldb.DB;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Dimension;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author LucGamesYT, geNAZt
 * @version 1.0
 */
public class ChunkCache {

    private final DB db;
    private final Object2ObjectMap<Dimension, Long2ObjectMap<Chunk>> cachedChunks;
    private final Object2ObjectMap<Dimension, Long2ObjectMap<CompletableFuture<Chunk>>> chunkFutures;

    public ChunkCache( DB db ) {
        this.db = db;
        this.cachedChunks = new Object2ObjectOpenHashMap<>( 8 * 8, Hash.VERY_FAST_LOAD_FACTOR );
        this.chunkFutures = new Object2ObjectOpenHashMap<>( 8 * 8, Hash.VERY_FAST_LOAD_FACTOR );

        for ( Dimension dimension : Dimension.values() ) {
            this.cachedChunks.put( dimension, new Long2ObjectOpenHashMap<>() );
            this.chunkFutures.put( dimension, new Long2ObjectOpenHashMap<>() );
        }
    }

    public synchronized void putFuture( int chunkX, int chunkZ, Dimension dimension, CompletableFuture<Chunk> chunkFuture ) {
        this.chunkFutures.get( dimension ).put( Utils.toLong( chunkX, chunkZ ), chunkFuture );
    }

    public synchronized CompletableFuture<Chunk> getFuture( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkFutures.get( dimension ).get( Utils.toLong( chunkX, chunkZ ) );
    }

    public synchronized void removeFuture( int chunkX, int chunkZ, Dimension dimension ) {
        this.chunkFutures.get( dimension ).remove( Utils.toLong( chunkX, chunkZ ) );
    }

    public synchronized Chunk getChunk( int chunkX, int chunkZ, Dimension dimension ) {
        long chunkHash = Utils.toLong( chunkX, chunkZ );

        CompletableFuture<Chunk> chunkFuture = this.chunkFutures.get( dimension ).get( chunkHash );
        if ( chunkFuture != null ) {
            try {
                return chunkFuture.get();
            } catch ( InterruptedException | ExecutionException e ) {
                return null;
            }
        }

        return this.cachedChunks.get( dimension ).get( chunkHash );
    }

    public synchronized boolean putChunk( Chunk chunk, Dimension dimension ) {
        long hash = Utils.toLong( chunk.getChunkX(), chunk.getChunkZ() );
        Long2ObjectMap<Chunk> dimensionChunks = this.cachedChunks.get( dimension );

        Chunk adapterChunk = dimensionChunks.get( hash );
        if ( adapterChunk == null ) {
            dimensionChunks.put( hash, chunk );
            return true;
        }

        return adapterChunk == chunk;
    }

    public synchronized boolean isChunkLoaded( int chunkX, int chunkZ, Dimension dimension ) {
        return this.cachedChunks.get( dimension ).containsKey( Utils.toLong( chunkX, chunkZ ) );
    }

    public synchronized boolean isChunkLoading( int chunkX, int chunkZ, Dimension dimension ) {
        return this.chunkFutures.get( dimension ).containsKey( Utils.toLong( chunkX, chunkZ ) );
    }

    public synchronized void clearChunks() {
        this.cachedChunks.clear();
    }

    public synchronized Collection<Chunk> getChunks( Dimension dimension ) {
        return this.cachedChunks.get( dimension ).values();
    }

    public Object2ObjectMap<Dimension, Long2ObjectMap<Chunk>> getCachedChunks() {
        return this.cachedChunks;
    }

    public Object2ObjectMap<Dimension, Long2ObjectMap<CompletableFuture<Chunk>>> getChunkFutures() {
        return this.chunkFutures;
    }

    public void saveAll() {
        Object2ObjectMap<Dimension, Long2ObjectMap<Chunk>> cachedChunks = new Object2ObjectOpenHashMap<>(this.cachedChunks);
        cachedChunks.replaceAll( ( k, v ) -> new Long2ObjectOpenHashMap<>( v ) );
        for ( Map.Entry<Dimension, Long2ObjectMap<Chunk>> entry : cachedChunks.entrySet() ) {
            for ( Chunk chunk : entry.getValue().values() ) {
                if ( chunk != null ) {
                    chunk.save( this.db );
                }
            }
        }
    }
}
