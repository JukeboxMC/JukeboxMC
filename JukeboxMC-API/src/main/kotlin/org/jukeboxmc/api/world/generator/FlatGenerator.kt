package org.jukeboxmc.api.world.generator

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.api.world.chunk.Chunk

class FlatGenerator : Generator() {

    private val blockGrass: Block = Block.create(BlockType.GRASS)
    private val blockDirt: Block = Block.create(BlockType.DIRT)
    private val blockBedrock: Block = Block.create(BlockType.BEDROCK)

    override fun generate(chunk: Chunk, chunkX: Int, chunkZ: Int) {
        for (blockX in 0..15) {
            for (blockZ in 0..15) {
                for (blockY in 0..15) {
                    chunk.setBiome(blockX, blockY, blockZ, Biome.PLAINS)
                }
                chunk.setBlock(blockX, -64, blockZ, 0, blockBedrock)
                chunk.setBlock(blockX, -63, blockZ, 0, blockDirt)
                chunk.setBlock(blockX, -62, blockZ, 0, blockDirt)
                chunk.setBlock(blockX, -61, blockZ, 0, blockGrass)
            }
        }
    }

    override fun populate(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int) {

    }

    override fun finish(manager: PopulationChunkManager, chunkX: Int, chunkZ: Int) {

    }

    override fun spawnLocation(): Vector {
        return Vector(0.5f, -60F, 0.5f)
    }

}