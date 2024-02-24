package org.jukeboxmc.server.world.chunk.manager

import com.google.common.base.Preconditions
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.generator.PopulationChunkManager

class JukeboxPopulationChunkManager(
    private val chunk: Chunk,
    private val chunkList: List<Chunk>
) : PopulationChunkManager {

    private var cornerX = 0
    private var cornerZ = 0
    private val chunks = arrayOfNulls<Chunk>(3 * 3)

    init {
        this.cornerX = this.chunk.getX() - 1
        this.cornerZ = this.chunk.getZ() - 1
        for (value in this.chunkList) {
            this.chunks[this.chunkIndex(value.getX(), value.getZ())] = value
        }
    }

    override fun getChunk(x: Int, z: Int): Chunk {
        return chunks[chunkIndex(x, z)]!!
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return chunkFromBlock(x, z)!!.getBlock(x, y, z, 0)
    }

    override fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block {
        return chunkFromBlock(x, z)!!.getBlock(x, y, z, layer)
    }

    override fun getBlock(location: Vector, layer: Int): Block {
        return chunkFromBlock(location.getBlockX(), location.getBlockZ())!!.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer)
    }

    override fun getBlock(location: Vector): Block {
        return chunkFromBlock(location.getBlockX(), location.getBlockZ())!!.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0)
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block) {
        chunkFromBlock(x, z)!!.setBlock(x, y, z, layer, block)
    }

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        chunkFromBlock(x, z)!!.setBlock(x, y, z, 0, block)
    }

    override fun setBlock(location: Vector, layer: Int, block: Block) {
        chunkFromBlock(location.getBlockX(), location.getBlockZ())!!.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, block)
    }

    override fun setBlock(location: Vector, block: Block) {
        chunkFromBlock(location.getBlockX(), location.getBlockZ())!!.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, block)
    }

    private fun chunkIndex(chunkX: Int, chunkZ: Int): Int {
        val relativeX: Int = chunkX - this.cornerX
        val relativeZ: Int = chunkZ - this.cornerZ
        Preconditions.checkArgument(
            relativeX in 0..2 && relativeZ >= 0 && relativeZ < 3,
            "Chunk position (%s,%s) out of population bounds",
            chunkX,
            chunkZ
        )
        return relativeX * 3 + relativeZ
    }

    private fun chunkFromBlock(blockX: Int, blockZ: Int): Chunk? {
        val relativeX: Int = (blockX shr 4) - this.cornerX
        val relativeZ: Int = (blockZ shr 4) - this.cornerZ
        Preconditions.checkArgument(
            relativeX in 0..2 && relativeZ >= 0 && relativeZ < 3,
            "Block position (%s,%s) out of population bounds",
            blockX,
            blockZ
        )
        return chunks[relativeX * 3 + relativeZ]
    }
}