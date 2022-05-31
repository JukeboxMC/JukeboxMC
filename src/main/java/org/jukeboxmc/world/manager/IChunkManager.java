package org.jukeboxmc.world.manager;

import org.jukeboxmc.world.chunk.Chunk;

public interface IChunkManager {

    Chunk getChunk( int x, int z );

}
