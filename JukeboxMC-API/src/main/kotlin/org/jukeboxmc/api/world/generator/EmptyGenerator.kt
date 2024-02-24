package org.jukeboxmc.api.world.generator

import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.chunk.Chunk

class EmptyGenerator: Generator() {

    override fun generate(chunk: Chunk, chunkX: Int, chunkZ: Int) {

    }

    override fun populate(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int) {

    }

    override fun finish(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int) {

    }

    override fun spawnLocation(): Vector {
        return Vector(0, 64, 0)
    }
}