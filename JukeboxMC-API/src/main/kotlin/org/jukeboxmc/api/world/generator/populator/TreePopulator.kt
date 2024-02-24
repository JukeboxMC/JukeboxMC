package org.jukeboxmc.api.world.generator.populator

import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.generator.PopulationChunkManager
import org.jukeboxmc.api.world.generator.feature.tree.Tree
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
class TreePopulator(private val tree: Tree) : Populator() {

    override fun populate(random: Random, manager: PopulationChunkManager, chunk: Chunk, chunkX: Int, chunkZ: Int) {
        val x = random.nextInt((chunkX shl 4), (chunkX shl 4) + 15)
        val z = random.nextInt((chunkZ shl 4), (chunkZ shl 4) + 15)
        val y = this.getHighestWorkableBlock(manager, chunk, x, z)

        if (y != -1) {
            try {
                this.tree.create(random, manager, x, y, z)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}