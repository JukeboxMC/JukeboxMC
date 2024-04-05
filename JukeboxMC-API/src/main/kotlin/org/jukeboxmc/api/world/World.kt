package org.jukeboxmc.api.world

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.gamerule.GameRule
import org.jukeboxmc.api.world.gamerule.GameRuleValue
import org.jukeboxmc.api.world.generator.Generator

interface World {

    /**
     * The world data of this world
     *
     * @return fresh world data
     */
    fun getWorldData(): WorldData

    fun getCurrentTick(): Long

    fun getName(): String

    fun setName(name: String)

    fun getTime(): Int

    fun setTime(time: Int)

    fun getDifficulty(): Difficulty

    fun setDifficulty(difficulty: Difficulty)

    fun getGameRules(): List<GameRuleValue>

    fun <V>getGameRule(gameRule: GameRule): V

    fun setGameRule(gameRule: GameRule, value: Any)

    fun getBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension): Block

    fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block

    fun getBlock(x: Int, y: Int, z: Int): Block

    fun getBlock(location: Vector, layer: Int, dimension: Dimension): Block

    fun getBlock(location: Vector, layer: Int): Block

    fun getBlock(location: Vector): Block

    fun getHighestBlockYAt(x: Int, z: Int, dimension: Dimension): Int

    fun getHighestBlockYAt(x: Int, z: Int): Int

    fun setBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension, block: Block, update: Boolean)

    fun setBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension, block: Block)

    fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block)

    fun setBlock(x: Int, y: Int, z: Int, block: Block)

    fun setBlock(location: Vector, layer: Int, dimension: Dimension, block: Block, update: Boolean)

    fun setBlock(location: Vector, layer: Int, dimension: Dimension, block: Block)

    fun setBlock(location: Vector, layer: Int, block: Block)

    fun setBlock(location: Vector, block: Block, layer: Int)

    fun setBlock(location: Vector, block: Block, layer: Int, update: Boolean)

    fun setBlock(location: Vector, block: Block)

    fun getBiome(x: Int, y: Int, z: Int, dimension: Dimension): Biome

    fun getBiome(x: Int, y: Int, z: Int): Biome

    fun getBiome(location: Vector): Biome

    fun getBiome(location: Vector, dimension: Dimension): Biome

    fun setBiome(x: Int, y: Int, z: Int, dimension: Dimension, biome: Biome)

    fun setBiome(x: Int, y: Int, z: Int, biome: Biome)

    fun setBiome(location: Vector, dimension: Dimension, biome: Biome)

    fun setBiome(location: Vector, biome: Biome)

    fun getBlockEntities(dimension: Dimension): Collection<BlockEntity>

    fun getBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension): BlockEntity?

    fun getBlockEntity(location: Vector): BlockEntity?

    fun setBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension, blockEntity: BlockEntity)

    fun setBlockEntity(location: Vector, blockEntity: BlockEntity)

    fun removeBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension)

    fun removeBlockEntity(location: Vector)

    fun isChunkLoaded(x: Int, z: Int, dimension: Dimension): Boolean

    fun getChunk(x: Int, z: Int, dimension: Dimension): Chunk?

    fun getLoadedChunk(x: Int, z: Int, dimension: Dimension): Chunk?

    fun getLoadedChunks(dimension: Dimension): Set<Chunk>

    fun getChunkPlayers(x: Int, z: Int, dimension: Dimension): Collection<Player>

    fun getGenerator(dimension: Dimension): Generator

    fun getSpawnLocation(dimension: Dimension): Location

    fun getEntityById(entityId: Long): Entity?

    fun getPlayers(): Collection<Player>

    fun getEntities(): Collection<Entity>

    fun spawnParticle(player: Player?, particle: Particle, location: Vector, data: Int)

    fun spawnParticle(player: Player?, particle: Particle, location: Vector)

    fun spawnParticle(particle: Particle, location: Vector, data: Int)

    fun spawnParticle(particle: Particle, location: Vector)

    fun getNearbyEntities(boundingBox: AxisAlignedBB, dimension: Dimension, entity: Entity?): Collection<Entity>

    fun getCollisionCubes(boundingBox: AxisAlignedBB, entity: Entity): Collection<AxisAlignedBB>

    fun dropItem(location: Location, item: Item)

    fun dropItemNaturally(location: Location, item: Item)

    fun save()

    /**
     * Closes this world by closing the level database connection
     */
    fun close()

}