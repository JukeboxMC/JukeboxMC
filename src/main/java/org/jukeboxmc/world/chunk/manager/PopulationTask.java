package org.jukeboxmc.world.chunk.manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.ChunkState;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.BiFunction;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@NoArgsConstructor ( access = AccessLevel.PRIVATE )
public class PopulationTask implements BiFunction<Chunk, List<Chunk>, Chunk> {

    public static final PopulationTask INSTANCE = new PopulationTask();

    @Override
    public @NotNull Chunk apply(@NotNull Chunk chunk, @NotNull List<Chunk> chunks ) {
        if ( chunk.isPopulated() ) {
            return chunk;
        }
        chunks.add( chunk );

        Set<Lock> locks = new HashSet<>();
        for ( Chunk value : chunks ) {
            Lock lock = value.getWriteLock();
            lock.lock();
            locks.add( lock );
        }
        try {
            chunk.getWorld().getGenerator( chunk.getDimension() ).populate( new PopulationChunkManager( chunk, chunks ), chunk.getX(), chunk.getZ() );
            chunk.setChunkState( ChunkState.FINISHED );
            chunk.setDirty( true );
        } finally {
            for ( Lock lock : locks ) {
                lock.unlock();
            }
        }

        return chunk;
    }
}
