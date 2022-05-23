package org.jukeboxmc.world.manager;

import lombok.RequiredArgsConstructor;
import org.jukeboxmc.world.chunk.Chunk;

@RequiredArgsConstructor
public final class SingleChunkManager implements IChunkManager {

    private final int x;
    private final int z;
    private final Chunk chunk;

    @Override
    public Chunk getChunk( int x, int z ) {
        return this.x == x && this.z == z ? this.chunk : null;
    }

}
