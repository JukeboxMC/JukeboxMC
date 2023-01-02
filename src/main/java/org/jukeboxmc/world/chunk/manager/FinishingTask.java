package org.jukeboxmc.world.chunk.manager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
 *
 */
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class FinishingTask implements BiFunction<Chunk, List<Chunk>, Chunk> {

    public static final FinishingTask INSTANCE = new FinishingTask();

    @Override
    public Chunk apply( Chunk chunk, List<Chunk> chunks ) {
        if (chunk.isFinished()) {
            return chunk;
        }
        chunks.add( chunk );

        Set<Lock> locks = new HashSet<>();
        for ( Chunk value : chunks ) {
            //Lock lock = value.getWriteLock();
           // lock.lock();
          //  locks.add( lock );
        }
        try {
            chunk.getWorld().getGenerator( chunk.getDimension() ).finish( new PopulationChunkManager( chunk, chunks ), chunk.getX(), chunk.getZ() );
            chunk.setChunkState( ChunkState.FINISHED );
            chunk.setDirty( true );
        } finally {
            for ( Lock lock : locks ) {
               // lock.unlock();
            }
        }

        return chunk;
    }
}
