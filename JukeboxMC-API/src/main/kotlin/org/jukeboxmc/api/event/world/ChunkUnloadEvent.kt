package org.jukeboxmc.api.event.world

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk

class ChunkUnloadEvent(
    world: World,
    private val chunk: Chunk,
    private var saveChunk: Boolean
) : WorldEvent(world), Cancellable {

    fun getChunk(): Chunk {
        return this.chunk
    }

    fun saveChunk(): Boolean {
        return this.saveChunk
    }

    fun setSaveChunk(saveChunk: Boolean) {
        this.saveChunk = saveChunk
    }
}