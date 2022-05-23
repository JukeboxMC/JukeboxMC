package org.jukeboxmc.world.generator;

import lombok.RequiredArgsConstructor;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class Generator {

    private World world;
    protected final Chunk[] chunks = new Chunk[9];

    protected int centerX = Integer.MAX_VALUE;
    protected int centerZ = Integer.MAX_VALUE;

    public synchronized final void init( World world, Chunk chunk, Chunk[] chunks ) {
        this.init( world );
        this.centerX = chunk.getChunkX();
        this.centerZ = chunk.getChunkZ();
        System.arraycopy( chunks, 0, this.chunks, 0, this.chunks.length );
    }

    protected synchronized void init( World world ) {
        this.world = world;
    }

    public synchronized final void clear() {
        this.centerX = Integer.MAX_VALUE;
        this.centerZ = Integer.MAX_VALUE;
        Arrays.fill( this.chunks, null );
    }

    public World getWorld() {
        return this.world;
    }

    public final Chunk getChunk( int x, int z ) {
        if ( this.centerX == Integer.MAX_VALUE || this.centerZ == Integer.MAX_VALUE )
            throw new UnsupportedOperationException( "This generation has not been initialized!" );

        final int nX = x - this.centerX;
        final int nZ = z - this.centerZ;

        int index = 0;
        switch ( nZ ) {
            case 0 -> index = 1;
            case 1 -> index = 2;
        }
        switch ( nX ) {
            case 0 -> index += 3;
            case 1 -> index += 6;
        }

        return this.chunks[index];
    }


    public abstract void generate( int chunkX, int chunkZ );

    public abstract void populate( int chunkX, int chunkZ );

    public abstract Vector getSpawnLocation();
}
