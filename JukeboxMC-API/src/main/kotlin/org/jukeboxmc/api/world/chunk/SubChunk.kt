package org.jukeboxmc.api.world.chunk

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Biome

interface SubChunk {

    fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block

    fun getBlock(x: Int, y: Int, z: Int): Block

    fun getBlock(location: Vector, layer: Int): Block

    fun getBlock(location: Vector): Block

    fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block)

    fun setBlock(x: Int, y: Int, z: Int, block: Block)

    fun setBlock(location: Vector, layer: Int, block: Block)

    fun setBlock(location: Vector, block: Block)

    fun getBiome(x: Int, y: Int, z: Int): Biome

    fun getBiome(location: Vector): Biome

    fun setBiome(x: Int, y: Int, z: Int, biome: Biome)

    fun setBiome(location: Vector, biome: Biome)

    fun isEmptySubChunk(): Boolean

    fun getLayer(): Int

    fun getSubChunkVersion(): Int

    fun getSubChunkIndex(): Int

}