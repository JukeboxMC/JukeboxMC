package org.jukeboxmc.api.event.world

import org.jukeboxmc.api.event.Cancellable
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk

class ChunkLoadEvent(
    world: World,
    private val chunk: Chunk?
) : WorldEvent(world), Cancellable {

    fun getChunk(): Chunk? {
        return this.chunk
    }
}