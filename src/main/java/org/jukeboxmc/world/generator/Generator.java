package org.jukeboxmc.world.generator;

import lombok.RequiredArgsConstructor;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.manager.IChunkManager;

import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Generator {

    protected final World world;
    protected final Dimension dimension;
    protected IChunkManager chunkManager;

    public synchronized final void init( IChunkManager chunkManager ) {
        this.chunkManager = chunkManager;
    }

    public synchronized final void clean() {
        this.chunkManager = null;
    }

    public World getWorld() {
        return this.world;
    }

    protected final Chunk getChunk( int x, int z ) {
        return this.chunkManager.getChunk( x, z );
    }

    public abstract void generate( int chunkX, int chunkZ );

    public abstract void populate( int chunkX, int chunkZ );

    public abstract Vector getSpawnLocation();
}
