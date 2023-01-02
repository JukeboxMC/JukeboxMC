package org.jukeboxmc.world.chunk.manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkState;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class GenerationTask implements Function<Chunk, Chunk> {

    public static final GenerationTask INSTANCE = new GenerationTask();

    @Override
    public Chunk apply( Chunk chunk ) {
        if (chunk.isGenerated()) {
            return chunk;
        }

        Lock writeLock = chunk.getWriteLock();
        writeLock.lock();
        try {
            chunk.getWorld().getGenerator( chunk.getDimension() ).generate( chunk, chunk.getX(), chunk.getZ() );
            chunk.setChunkState( ChunkState.GENERATED );
            chunk.setDirty( true );
        } finally {
            writeLock.unlock();
        }

        return chunk;
    }
}
