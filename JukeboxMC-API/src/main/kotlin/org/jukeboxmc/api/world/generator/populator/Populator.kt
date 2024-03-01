package org.jukeboxmc.api.world.generator.populator

import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.generator.PopulationChunkManager
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
abstract class Populator {

    abstract fun populate(random: Random, manager: PopulationChunkManager, chunk: Chunk, chunkX: Int, chunkZ: Int)

    fun blockBelow(chunk: Chunk, x: Int, y: Int, z: Int, blockType: BlockType): Boolean = chunk.getBlock(x, y - 1, z, 0).getType() == blockType

    fun getHighestWorkableBlock(manager: PopulationChunkManager, chunk: Chunk, x: Int, z: Int): Int {
        var height = 0

        for (y in chunk.getMaxY() downTo chunk.getMinY()) {
            val block = manager.getBlock(x, y, z, 0)

            if (block.getType() != BlockType.AIR && !block.getType().isLeaves() && block.getType() != BlockType.AZALEA_LEAVES && block.getType() != BlockType.AZALEA_LEAVES_FLOWERED && block.getType() != BlockType.MANGROVE_LEAVES && block.getType() != BlockType.SNOW_LAYER) {
                break
            }

            height = y
        }

        return if (height == 0) -1 else ++height
    }
}