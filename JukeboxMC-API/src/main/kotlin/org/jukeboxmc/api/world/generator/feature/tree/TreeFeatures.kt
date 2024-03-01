package org.jukeboxmc.api.world.generator.feature.tree

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.world.generator.populator.TreePopulator

/**
 * @author Kaooot
 * @version 1.0
 */
object TreeFeatures {

    // TODO update leaf type if necessary
    fun createOak(): TreePopulator = TreePopulator(StraightBlobTree(Block.create(BlockType.OAK_LOG), Block.create(BlockType.OAK_LEAVES), 4, 2, 0, 2))

    fun createBirch(): TreePopulator = TreePopulator(StraightBlobTree(Block.create(BlockType.BIRCH_LOG), Block.create(BlockType.BIRCH_LEAVES), 5, 2, 0, 2))

    fun createSuperBirch(): TreePopulator = TreePopulator(StraightBlobTree(Block.create(BlockType.BIRCH_LOG), Block.create(BlockType.BIRCH_LEAVES), 5, 2, 6, 2))

    fun createJungle(): TreePopulator = TreePopulator(StraightBlobTree(Block.create(BlockType.JUNGLE_LOG), Block.create(BlockType.JUNGLE_LEAVES), 4, 8, 0, 2))

    fun createSwampOak(): TreePopulator = TreePopulator(StraightBlobTree(Block.create(BlockType.OAK_LOG), Block.create(BlockType.OAK_LEAVES), 5, 3, 0, 3))
}