package org.jukeboxmc.world.chunk.manager;

import com.google.common.collect.ImmutableSet;
import com.spotify.futures.CompletableFutures;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.world.ChunkLoadEvent;
import org.jukeboxmc.event.world.ChunkUnloadEvent;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import javax.annotation.ParametersAreNonnullByDefault;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Log4j2
@ParametersAreNonnullByDefault
public final class ChunkManager {
    private static final CompletableFuture<Void> COMPLETED_VOID_FUTURE = CompletableFuture.completedFuture( null );
    private static final AtomicIntegerFieldUpdater<LoadingChunk> GENERATION_RUNNING_UPDATER = AtomicIntegerFieldUpdater.newUpdater( LoadingChunk.class, "generationRunning" );
    private static final AtomicIntegerFieldUpdater<LoadingChunk> POPULATION_RUNNING_UPDATER = AtomicIntegerFieldUpdater.newUpdater( LoadingChunk.class, "populationRunning" );
    private static final AtomicIntegerFieldUpdater<LoadingChunk> FINISH_RUNNING_UPDATER = AtomicIntegerFieldUpdater.newUpdater( LoadingChunk.class, "finishRunning" );

    private final World world;
    private final Dimension dimension;
    private final Long2ObjectMap<LoadingChunk> chunks = new Long2ObjectOpenHashMap<>();
    private final Long2LongMap chunkLoadedTimes = new Long2LongOpenHashMap();
    private final Long2LongMap chunkLastAccessTimes = new Long2LongOpenHashMap();
    private final Executor executor;

    public ChunkManager( World world, Dimension dimension ) {
        this.world = world;
        this.dimension = dimension;
        this.executor = this.world.getServer().getScheduler().getChunkExecutor();
    }

    public synchronized Set<Chunk> getLoadedChunks() {
        ImmutableSet.Builder<Chunk> chunks = ImmutableSet.builder();
        for ( LoadingChunk loadingChunk : this.chunks.values() ) {
            Chunk chunk = loadingChunk.getChunk();
            if ( chunk != null ) {
                chunks.add( chunk );
            }
        }
        return chunks.build();
    }

    public synchronized int getLoadedCount() {
        return this.chunks.size();
    }

    public synchronized Chunk getLoadedChunk( long key ) {
        LoadingChunk chunk = this.chunks.get( key );
        return chunk == null ? null : chunk.getChunk();
    }

    public synchronized Chunk getLoadedChunk( int x, int z ) {
        return getLoadedChunk( Utils.toLong( x, z ) );
    }

    public Chunk getChunk( int x, int z ) {
        try {
            Chunk chunk = this.getLoadedChunk( x, z );
            if ( chunk == null ) {
                chunk = this.getChunkFuture( x, z ).join();
            }
            return chunk;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public CompletableFuture<Chunk> getChunkFuture( int x, int z ) {
        return this.getChunkFuture( x, z, true, true, true );
    }

    private synchronized CompletableFuture<Chunk> getChunkFuture( int chunkX, int chunkZ, boolean generate, boolean populate, boolean finish ) {
        final long chunkKey = Utils.toLong( chunkX, chunkZ );
        this.chunkLastAccessTimes.put( chunkKey, System.currentTimeMillis() );
        LoadingChunk chunk = this.chunks.computeIfAbsent( chunkKey, key -> new LoadingChunk( this.dimension, key, true ) );

        if ( finish ) {
            chunk.finish();
        } else if ( populate ) {
            chunk.populate();
        } else if ( generate ) {
            chunk.generate();
        }

        Server.getInstance().getPluginManager().callEvent( new ChunkLoadEvent( this.world, chunk.getChunk() ) );

        return chunk.getFuture();
    }

    public synchronized boolean isChunkLoaded( long hash ) {
        LoadingChunk chunk = this.chunks.get( hash );
        return chunk != null && chunk.getChunk() != null;
    }

    public synchronized boolean isChunkLoaded( int x, int z ) {
        return this.isChunkLoaded( Utils.toLong( x, z ) );
    }

    public synchronized boolean unloadChunk( long hash ) {
        return this.unloadChunk( hash, true, true );
    }

    public boolean unloadChunk( Chunk chunk ) {
        return this.unloadChunk( chunk, true );
    }

    public boolean unloadChunk( Chunk chunk, boolean save ) {
        return this.unloadChunk( chunk, save, true );
    }

    public boolean unloadChunk( Chunk chunk, boolean save, boolean safe ) {
        return this.unloadChunk( Utils.toLong( chunk.getX(), chunk.getZ() ), save, safe );
    }

    public boolean unloadChunk( long chunkKey, boolean save, boolean safe ) {
        Chunk loadedChunk = this.getLoadedChunk( chunkKey );
        if ( loadedChunk == null ) return false;
        ChunkUnloadEvent chunkUnloadEvent = new ChunkUnloadEvent( this.world, loadedChunk, save );
        Server.getInstance().getPluginManager().callEvent( chunkUnloadEvent );
        if ( chunkUnloadEvent.isCancelled() ) return false;
        boolean result = unloadChunk0( chunkKey, chunkUnloadEvent.isSaveChunk(), safe );
        if ( result ) {
            this.chunks.remove( chunkKey );
        }
        return result;
    }

    private synchronized boolean unloadChunk0( long chunkKey, boolean save, boolean safe ) {
        LoadingChunk loadingChunk = this.chunks.get( chunkKey );
        if ( loadingChunk == null ) {
            return false;
        }
        Chunk chunk = loadingChunk.getChunk();
        if ( chunk == null ) {
            return false;
        }
        if ( !chunk.getLoaders().isEmpty() ) {
            return false;
        }

        if ( save ) {
            this.saveChunk( chunk );
        }

        if ( safe && !this.world.getChunkPlayers( chunk.getX(), chunk.getZ(), chunk.getDimension() ).isEmpty() ) {
            return false;
        }

        this.chunkLastAccessTimes.remove( chunkKey );
        this.chunkLoadedTimes.remove( chunkKey );

        return true;
    }

    public synchronized CompletableFuture<Void> saveChunks() {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for ( LoadingChunk loadingChunk : this.chunks.values() ) {
            Chunk chunk = loadingChunk.getChunk();
            if ( chunk != null ) {
                futures.add( saveChunk( chunk ) );
            }
        }

        return CompletableFuture.allOf( futures.toArray( new CompletableFuture[0] ) );
    }

    public CompletableFuture<Void> saveChunk( Chunk chunk ) {
        if ( chunk.isDirty() ) {
            return this.world.saveChunk( chunk ).exceptionally( throwable -> {
                log.warn( "Unable to save chunk", throwable );
                return null;
            } );
        }
        return COMPLETED_VOID_FUTURE;
    }

    public synchronized void tick() {
        if ( this.chunks.isEmpty() ) {
            return;
        }

        long time = System.currentTimeMillis();

        // Spawn chunk
        final int spawnX = this.world.getSpawnLocation().getChunkX();
        final int spawnZ = this.world.getSpawnLocation().getChunkZ();
        final int spawnRadius = Server.getInstance().getViewDistance();

        // Do chunk garbage collection
        ObjectIterator<Long2ObjectMap.Entry<LoadingChunk>> iterator = this.chunks.long2ObjectEntrySet().iterator();
        while ( iterator.hasNext() ) {
            Long2ObjectMap.Entry<LoadingChunk> entry = iterator.next();
            long chunkKey = entry.getLongKey();
            LoadingChunk loadingChunk = entry.getValue();
            Chunk chunk = loadingChunk.getChunk();
            if ( chunk == null ) {
                continue; // Chunk hasn't loaded
            }

            if ( ( Math.abs( chunk.getX() - spawnX ) <= spawnRadius && Math.abs( chunk.getZ() - spawnZ ) <= spawnRadius ) ||
                    !chunk.getLoaders().isEmpty() ) {
                continue; // Spawn protection or is loaded
            }

            long loadedTime = this.chunkLoadedTimes.get( chunkKey );
            if ( ( time - loadedTime ) <= TimeUnit.SECONDS.toMillis( 30 ) ) {
                continue;
            }

            long lastAccessTime = this.chunkLastAccessTimes.get( chunkKey );
            if ( ( time - lastAccessTime ) <= TimeUnit.SECONDS.toMillis( 120 ) ) {
                continue;
            }

            ChunkUnloadEvent chunkUnloadEvent = new ChunkUnloadEvent( this.world, chunk, true );
            Server.getInstance().getPluginManager().callEvent( chunkUnloadEvent );

            if ( chunkUnloadEvent.isCancelled() ) {
                return;
            }

            if ( this.unloadChunk0( chunkKey, chunkUnloadEvent.isSaveChunk(), true ) ) {
                iterator.remove();
            }
        }
    }

    @ToString
    private class LoadingChunk {

        private final int x;
        private final int z;
        private CompletableFuture<Chunk> future;
        volatile int generationRunning;
        volatile int populationRunning;
        volatile int finishRunning;
        private Chunk chunk;

        public LoadingChunk( Dimension dimension, long key, boolean load ) {
            this.x = Utils.fromHashX( key );
            this.z = Utils.fromHashZ( key );

            if ( load ) {
                this.future = ChunkManager.this.world.readChunk( new Chunk( ChunkManager.this.world, dimension, this.x, this.z ) )
                        .thenApply( chunk -> Objects.requireNonNullElseGet( chunk, () -> new Chunk( ChunkManager.this.world, dimension, this.x, this.z ) ) );
                this.future.whenComplete( ( chunk, throwable ) -> {
                    if ( throwable != null ) {
                        Server.getInstance().getLogger().error( "Unable to load chunk " + x + ":" + z, throwable );

                        synchronized (ChunkManager.this) {
                            ChunkManager.this.chunks.remove( key );
                        }
                    } else {
                        long currentTime = System.currentTimeMillis();
                        synchronized (ChunkManager.this) {
                            ChunkManager.this.chunkLoadedTimes.put( key, currentTime );
                        }
                    }
                } );
            } else {
                this.future = CompletableFuture.completedFuture( new Chunk( ChunkManager.this.world, dimension, this.x, this.z ) );
            }
            this.future.whenComplete( ( chunk, throwable ) -> this.chunk = chunk );
        }

        public CompletableFuture<Chunk> getFuture() {
            return future;
        }

        private Chunk getChunk() {
            if ( this.chunk != null && this.chunk.isGenerated() && this.chunk.isPopulated() && this.chunk.isFinished() ) {
                return this.chunk;
            }
            return null;
        }

        private void generate() {
            if ( ( this.chunk == null || !this.chunk.isGenerated() ) && GENERATION_RUNNING_UPDATER.compareAndSet( this, 0, 1 ) ) {
                future = future.thenApplyAsync( GenerationTask.INSTANCE, ChunkManager.this.executor );
                future.thenRun( () -> GENERATION_RUNNING_UPDATER.compareAndSet( this, 1, 0 ) );
            }
        }

        private void populate() {
            this.generate();
            if ( ( this.chunk == null || !this.chunk.isPopulated() ) && POPULATION_RUNNING_UPDATER.compareAndSet( this, 0, 1 ) ) {
                // Load and generate chunks around the chunk to be populated.
                List<CompletableFuture<Chunk>> chunksToLoad = new ArrayList<>( 8 );
                for ( int z = this.z - 1, maxZ = this.z + 1; z <= maxZ; z++ ) {
                    for ( int x = this.x - 1, maxX = this.x + 1; x <= maxX; x++ ) {
                        if ( x == this.x && z == this.z ) continue;
                        chunksToLoad.add( ChunkManager.this.getChunkFuture( x, z, true, false, false ) );
                    }
                }
                CompletableFuture<List<Chunk>> aroundFuture = CompletableFutures.allAsList( chunksToLoad );

                future = future.thenCombineAsync( aroundFuture, PopulationTask.INSTANCE, ChunkManager.this.executor );
                future.thenRun( () -> POPULATION_RUNNING_UPDATER.compareAndSet( this, 1, 0 ) );
            }
        }

        private void finish() {
            this.populate();
            if ( ( this.chunk == null || !this.chunk.isFinished() ) && FINISH_RUNNING_UPDATER.compareAndSet( this, 0, 1 ) ) {
                List<CompletableFuture<Chunk>> chunksToLoad = new ArrayList<>( 8 );
                for ( int z = this.z - 1, maxZ = this.z + 1; z <= maxZ; z++ ) {
                    for ( int x = this.x - 1, maxX = this.x + 1; x <= maxX; x++ ) {
                        if ( x == this.x && z == this.z ) continue;
                        chunksToLoad.add( ChunkManager.this.getChunkFuture( x, z, true, true, false ) );
                    }
                }
                CompletableFuture<List<Chunk>> aroundFuture = CompletableFutures.allAsList( chunksToLoad );

                future = future.thenCombineAsync( aroundFuture, FinishingTask.INSTANCE, ChunkManager.this.executor );
                future.thenRun( () -> FINISH_RUNNING_UPDATER.compareAndSet( this, 1, 0 ) );
            }
        }

        private void clear() {
            this.future = future.thenApply( chunk -> {
                GENERATION_RUNNING_UPDATER.set( this, 0 );
                POPULATION_RUNNING_UPDATER.set( this, 0 );
                FINISH_RUNNING_UPDATER.set( this, 0 );
                return chunk;
            } );
        }
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}
