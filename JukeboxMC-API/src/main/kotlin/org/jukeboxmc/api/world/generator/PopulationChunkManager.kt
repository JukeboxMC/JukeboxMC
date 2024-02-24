package org.jukeboxmc.api.world.generator

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.chunk.Chunk

interface PopulationChunkManager {

    fun getChunk(x: Int, z: Int): Chunk

    fun getBlock(x: Int, y: Int, z: Int): Block

    fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block

    fun getBlock(location: Vector, layer: Int): Block

    fun getBlock(location: Vector): Block

    fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block)

    fun setBlock(x: Int, y: Int, z: Int, block: Block)

    fun setBlock(location: Vector, layer: Int, block: Block)

    fun setBlock(location: Vector, block: Block)
}