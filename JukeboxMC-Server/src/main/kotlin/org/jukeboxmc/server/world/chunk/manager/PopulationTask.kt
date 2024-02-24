package org.jukeboxmc.server.world.chunk.manager

import org.jukeboxmc.server.world.chunk.ChunkState
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import java.util.concurrent.locks.Lock
import java.util.function.BiFunction


class PopulationTask : BiFunction<JukeboxChunk, MutableList<JukeboxChunk>, JukeboxChunk> {
    override fun apply(chunk: JukeboxChunk, chunks: MutableList<JukeboxChunk>): JukeboxChunk {
        if (chunk.isPopulated()) {
            return chunk
        }
        chunks.add(chunk)
        val locks: MutableSet<Lock> = HashSet()
        for (value in chunks) {
            val lock: Lock = value.getWriteLock()
            lock.lock()
            locks.add(lock)
        }
        try {
            chunk.getWorld().getGenerator(chunk.getDimension()).populate(JukeboxPopulationChunkManager(chunk, chunks), chunk.getX(), chunk.getZ())
            chunk.setChunkState(ChunkState.FINISHED)
            chunk.setDirty(true)
        } finally {
            for (lock in locks) {
                lock.unlock()
            }
        }
        return chunk
    }

    companion object {
        val INSTANCE = PopulationTask()
    }
}

