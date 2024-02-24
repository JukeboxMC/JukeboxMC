package org.jukeboxmc.api.world.chunk

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World

interface Chunk {

    fun getWorld(): World

    fun getDimension(): Dimension

    fun getX(): Int

    fun getZ(): Int

    fun getMinY(): Int

    fun getMaxY(): Int

    fun isGenerated(): Boolean

    fun isPopulated(): Boolean

    fun isFinished(): Boolean

    fun getPlayers(): Set<Player>

    fun getEntities(): Set<Entity>

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

    fun getBlockEntities(): Collection<BlockEntity>

    fun getBlockEntity(x: Int, y: Int, z: Int): BlockEntity?

    fun getBlockEntity(vector: Vector): BlockEntity?

    fun setBlockEntity(x: Int, y: Int, z: Int, blockEntity: BlockEntity)

    fun setBlockEntity(vector: Vector, blockEntity: BlockEntity)

    fun removeBlockEntity(x: Int, y: Int, z: Int)

    fun removeBlockEntity(vector: Vector)
}