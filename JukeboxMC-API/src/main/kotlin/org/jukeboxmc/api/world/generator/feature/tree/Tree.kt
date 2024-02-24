package org.jukeboxmc.api.world.generator.feature.tree

import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.world.generator.PopulationChunkManager
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
interface Tree {

    fun create(random: Random, manager: PopulationChunkManager, x: Int, y: Int, z: Int)

    fun maxFreeTreeHeight(manager: PopulationChunkManager, x: Int, y: Int, z: Int, treeHeight: Int): Int {
        for (i in 0 until treeHeight) {
            val sizeAtHeight = if (i < 1) 0 else 1

            for (j in -sizeAtHeight..sizeAtHeight) {
                for (k in -sizeAtHeight..sizeAtHeight) {
                    if (manager.getBlock(x + j, y + treeHeight + i, z + k, 0).getType() != BlockType.AIR) {
                        return i - 2
                    }
                }
            }
        }

        return treeHeight
    }
}