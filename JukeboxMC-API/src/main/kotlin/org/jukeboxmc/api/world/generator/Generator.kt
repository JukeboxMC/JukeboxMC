package org.jukeboxmc.api.world.generator

import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.chunk.Chunk


abstract class Generator {
    abstract fun generate(chunk: Chunk, chunkX: Int, chunkZ: Int)
    abstract fun populate(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int)
    abstract fun finish(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int)
    abstract fun spawnLocation(): Vector
}

