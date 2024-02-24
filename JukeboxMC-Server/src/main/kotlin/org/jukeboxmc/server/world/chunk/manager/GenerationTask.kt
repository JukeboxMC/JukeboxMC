package org.jukeboxmc.server.world.chunk.manager

import org.jukeboxmc.server.world.chunk.ChunkState
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import java.util.concurrent.locks.Lock
import java.util.function.Function


class GenerationTask : Function<JukeboxChunk, JukeboxChunk> {

    override fun apply(chunk: JukeboxChunk): JukeboxChunk {
        if (chunk.isGenerated()) {
            return chunk
        }
        val writeLock: Lock = chunk.getWriteLock()
        writeLock.lock()
        try {
            chunk.getWorld().getGenerator(chunk.getDimension()).generate(chunk, chunk.getX(), chunk.getZ())
            chunk.setChunkState(ChunkState.GENERATED)
            chunk.setDirty(true)
        } finally {
            writeLock.unlock()
        }
        return chunk
    }

    companion object {
        val INSTANCE = GenerationTask()
    }
}

