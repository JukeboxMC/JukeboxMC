package org.jukeboxmc.world.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.Generator;
import org.jukeboxmc.world.manager.PopulationChunkManager;
import org.jukeboxmc.world.manager.SingleChunkManager;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class ChunkManager {

    private final World world;
    private final Dimension dimension;
    private final Long2ObjectMap<LoadingChunk> chunks = new Long2ObjectOpenHashMap<>();

    public synchronized void update() {
        if ( this.chunks.isEmpty() ) {
            return;
        }

        for ( Iterator<LoadingChunk> chunkIterator = this.chunks.values().iterator(); chunkIterator.hasNext(); ) {
            LoadingChunk loadingChunk = chunkIterator.next();
            Chunk chunk = loadingChunk.getChunk();
            if ( chunk == null ) {
                continue;
            }

            final Collection<ChunkLoader> chunkLoaders = chunk.getLoaders();
            if ( chunkLoaders != null && !chunkLoaders.isEmpty() ) {
                if ( chunk.isChanged() ) {
                    for ( ChunkLoader chunkLoader : chunkLoaders ) {
                        if ( chunkLoader instanceof Player player ) {
                            player.sendChunk( chunk );
                        }
                    }

                    chunk.setChanged( false );
                }
                continue;
            }

            if ( this._unloadChunk( chunk, true, true ) ) {
                chunkIterator.remove();
            }
        }
    }

    public synchronized Chunk getLoadedChunk( int x, int z ) {
        LoadingChunk chunk = this.chunks.get( Utils.toLong( x, z ) );
        return chunk == null ? null : chunk.getChunk();
    }

    public synchronized Set<Chunk> getLoadedChunks() {
        Set<Chunk> chunks = new HashSet<>();
        for ( LoadingChunk chunk : this.chunks.values() ) {
            if ( chunk.getChunk() == null ) {
                continue;
            }

            chunks.add( chunk.getChunk() );
        }

        return Collections.unmodifiableSet( chunks );
    }

    public synchronized int getLoadedChunksCount() {
        int count = 0;

        for ( LoadingChunk chunk : this.chunks.values() ) {
            if ( chunk.getChunk() == null ) {
                continue;
            }

            count++;
        }

        return count;
    }

    public Chunk getChunk( int x, int z ) {
        Chunk chunk = this.getLoadedChunk( x, z );
        return chunk == null ? this.getChunkFuture( x, z ).join() : chunk;
    }

    public CompletableFuture<Chunk> getChunkFuture( int x, int z ) {
        return this.getChunkFuture( x, z, true, true, true );
    }

    public CompletableFuture<Chunk> getChunkFuture( int x, int z, boolean load ) {
        return this.getChunkFuture( x, z, load, true, true );
    }

    public synchronized CompletableFuture<Chunk> getChunkFuture( int x, int z, boolean load, boolean generate, boolean populate ) {
        LoadingChunk chunk = this.chunks.computeIfAbsent( Utils.toLong( x, z ), key -> new LoadingChunk( this.world, this.dimension, x, z, load ) );

        if ( populate ) chunk.populate( this );
        else if ( generate ) chunk.generate();

        return chunk.getFuture();
    }

    public synchronized boolean isChunkLoaded( int x, int z ) {
        return this.chunks.containsKey( Utils.toLong( x, z ) );
    }

    public void unloadChunk( int x, int z ) {
        this.unloadChunk( x, z, true, true );
    }

    public synchronized void unloadChunk( int x, int z, boolean safe, boolean save ) {
        LoadingChunk loadingChunk = this.chunks.get( Utils.toLong( x, z ) );

        Chunk chunk = null;
        if ( loadingChunk != null ) {
            if ( loadingChunk.getChunk() == null ) {
                chunk = loadingChunk.getFuture().join();
            } else {
                chunk = loadingChunk.getChunk();
            }
        }

        if ( chunk != null ) {
            if ( this._unloadChunk( chunk, safe, save ) ) {
                this.chunks.remove( Utils.toLong( x, z ) );
            }
        }
    }

    private boolean _unloadChunk( Chunk chunk, boolean safe, boolean save ) {
        /*
        if ( safe ) {
            if ( chunk.getPlayers().size() <= 0 ) {
                if ( save ) {
                    chunk.save( this.world.getDb(), false );
                }
                return true;
            } else {
                return false;
            }
        } else {
            if ( save ) {
                chunk.save( this.world.getDb(), false );
                return true;
            }
        }
        return !safe && !save;
         */
        return false;
    }

    public synchronized void close() {
        for ( LoadingChunk loadingChunk : this.chunks.values() ) {
            loadingChunk.getFuture().cancel( true );
        }
        this.chunks.clear();
    }

    @Getter
    public static final class LoadingChunk {

        private final World world;
        private final int x;
        private final int z;

        private CompletableFuture<Chunk> future;
        private Chunk chunk;

        private volatile boolean generating;
        private volatile boolean populating;

        public LoadingChunk( World world, Dimension dimension, int x, int z, boolean load ) {
            this.world = world;
            this.x = x;
            this.z = z;

            this.generating = false;
            this.populating = false;

            if ( load ) {
                this.future = CompletableFuture.supplyAsync( () -> {
                    Chunk chunk = new Chunk( world, x, z, dimension );
                    if ( this.world.loadChunk( chunk ) ) {
                        chunk.setGenerated( true );
                        chunk.setPopulated( true );
                    }
                    return chunk;
                } );
            } else {
                this.future = CompletableFuture.completedFuture( new Chunk( world, x, z, dimension ) );
            }

            this.future.whenComplete( ( chunk, throwable ) -> {
                if ( chunk != null ) {
                    this.chunk = chunk;
                }
            } );
        }

        public Chunk getChunk() {
            if ( this.chunk != null && this.chunk.isGenerated() && this.chunk.isPopulated() ) {
                return this.chunk;
            }

            return null;
        }

        private void generate() {
            if ( ( this.chunk == null || !this.chunk.isGenerated() ) && !this.isGenerating() ) {
                this.generating = true;

                this.future = this.future.thenApplyAsync( chunk -> {
                    if ( chunk.isGenerated() ) {
                        return chunk;
                    }

                    Generator generator = this.world.getGenerator( chunk.getDimension() );

                    try {
                        generator.init( new SingleChunkManager( this.getX(), this.getZ(), chunk ) );

                        generator.generate( this.getX(), this.getZ() );
                    } finally {
                        chunk.setGenerated( true );
                        generator.clean();
                    }

                    return chunk;
                } );
                this.future.thenRun( () -> this.generating = false );
            }
        }

        private void populate( ChunkManager chunkManager ) {
            this.generate();

            if ( ( this.chunk == null || !this.chunk.isPopulated() ) && !this.isPopulating() ) {
                this.populating = true;

                //noinspection unchecked
                CompletableFuture<Chunk>[] chunksToLoad = new CompletableFuture[9];
                for ( int x = -1; x <= 1; x++ ) {
                    for ( int z = -1; z <= 1; z++ ) {
                        if ( x == 0 && z == 0 ) continue;
                        chunksToLoad[( x + 1 ) + ( z + 1 ) * 3] = chunkManager.getChunkFuture( this.x + x, this.z + z, true, true, false );
                    }
                }

                this.future = this.future.thenApplyAsync( chunk -> {
                    if ( chunk.isPopulated() ) {
                        return chunk;
                    }

                    if ( !chunk.isGenerated() ) {
                        throw new UnsupportedOperationException( "Tried to populate a chunk that was not generated" );
                    }

                    Generator generator = this.world.getGenerator( chunk.getDimension() );

                    try {
                        Chunk[] chunksArray = new Chunk[9];
                        for ( int i = 0; i < chunksArray.length; i++ ) {
                            if ( i == 4 ) chunksArray[i] = chunk;
                            else chunksArray[i] = chunksToLoad[i].join();
                        }

                        generator.init( new PopulationChunkManager( this.getX(), this.getZ(), chunksArray ) );

                        generator.populate( this.getX(), this.getZ() );
                    } finally {
                        chunk.setPopulated( true );
                        generator.clean();
                    }

                    return chunk;
                } );
                this.future.thenRun( () -> this.populating = false );
            }
        }

    }

}