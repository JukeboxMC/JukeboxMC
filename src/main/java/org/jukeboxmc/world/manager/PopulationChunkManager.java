package org.jukeboxmc.world.manager;

import lombok.RequiredArgsConstructor;
import org.jukeboxmc.world.chunk.Chunk;

@RequiredArgsConstructor
public final class PopulationChunkManager implements IChunkManager {

    private final int centerX;
    private final int centerZ;
    private final Chunk[] chunks;

    @Override
    public Chunk getChunk( int x, int z ) {
        return this.chunks[( x - this.centerX + 1 ) + ( z - this.centerZ + 1 ) * 3];
    }

}
