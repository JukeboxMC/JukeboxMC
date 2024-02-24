package org.jukeboxmc.api.world.generator.feature.tree

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.generator.PopulationChunkManager
import java.util.*
import kotlin.math.abs

/**
 * @author Kaooot
 * @version 1.0
 */
class StraightBlobTree(
    private val trunkBlock: Block? = null,
    private val foliageBlock: Block? = null,
    private val baseHeight: Int = 0,
    private val firstRandomHeight: Int = 0,
    private val secondRandomHeight: Int = 0,
    private val foliageRadius: Int = 0
) : Tree {

    private val dirtBlock: Block = Block.create(BlockType.DIRT)

    override fun create(random: Random, manager: PopulationChunkManager, x: Int, y: Int, z: Int) {
        val treeHeight = this.maxFreeTreeHeight(manager, x, y, z, this.baseHeight + random.nextInt(this.firstRandomHeight + 1) + random.nextInt(this.secondRandomHeight + 1))

        if (!this.canBePlaced(manager, random, x, y, z, treeHeight)) {
            return
        }

        manager.setBlock(x, y - 1, z, this.dirtBlock)

        for (i in 0 until treeHeight) {
            manager.setBlock(x, y + i, z, this.trunkBlock!!)
        }

        val foliageOffset = 0
        val foliageHeight = 3
        val leaves: MutableList<Vector> = ArrayList()

        for (i in foliageOffset downTo foliageOffset - foliageHeight) {
            val radius = (this.foliageRadius + foliageOffset - 1 - i / 2).coerceAtLeast(0)

            for (j in -radius..radius) {
                for (k in -radius..radius) {
                    if (!this.shouldSkipLocationSigned(random, j, i, k, radius) && manager.getBlock(x + j, y + i + treeHeight, z + k, 0).getType() == BlockType.AIR) {
                        val vector = Vector(x + j, y + i + treeHeight, z + k)

                        manager.setBlock(vector, this.foliageBlock!!)

                        leaves.add(vector)
                    }
                }
            }
        }
    }

    private fun shouldSkipLocation(random: Random, dx: Int, y: Int, dz: Int, radius: Int): Boolean {
        return dx == radius && dz == radius && (random.nextInt(2) == 0 || y == 0)
    }

    private fun shouldSkipLocationSigned(random: Random, dx: Int, y: Int, dz: Int, radius: Int): Boolean {
        return this.shouldSkipLocation(random, abs(dx), y, abs(dz), radius)
    }

    private fun canBePlaced(manager: PopulationChunkManager, random: Random, x: Int, y: Int, z: Int, treeHeight: Int): Boolean {
        /*TODO if (!manager.getBlock(x, y - 1, z, 0).isSolid()) {
            return false
        }*/

        for (i in 0 until treeHeight) {
            if (manager.getBlock(x, y + i, z, 0).getType() != BlockType.AIR) {
                return false
            }
        }

        for (i in 0 downTo -3) {
            val radius = (this.foliageRadius - 1 - i / 2).coerceAtLeast(0)

            for (j in -radius..radius) {
                for (k in -radius..radius) {
                    if (manager.getBlock(x + j, y + treeHeight + i, z + k, 0).getType() != BlockType.AIR && !this.shouldSkipLocationSigned(random, j, i, k, radius)) {
                        return false
                    }
                }
            }
        }

        return true
    }
}