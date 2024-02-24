package org.jukeboxmc.server.world.chunk

import com.google.common.collect.ImmutableSet
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.Biome
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.palette.Palette
import org.jukeboxmc.server.entity.JukeboxEntity
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxBlockEntity
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.Utils
import org.jukeboxmc.server.world.chunk.serializer.BiomeIdSerializer
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.function.Function
import kotlin.math.abs

class JukeboxChunk(
    private val world: World,
    private val dimension: Dimension,
    private val chunkX: Int,
    private val chunkZ: Int
) : Chunk {

    private var minY: Int = this.dimension.getMinY()
    private var maxY: Int = this.dimension.getMaxY()
    private var fullHeight: Int = abs(this.minY) + this.maxY
    private var entities: CopyOnWriteArraySet<JukeboxEntity> = CopyOnWriteArraySet<JukeboxEntity>()
    private var players: CopyOnWriteArraySet<JukeboxPlayer> = CopyOnWriteArraySet<JukeboxPlayer>()
    private val blockEntities: Int2ObjectMap<BlockEntity> = Int2ObjectOpenHashMap()
    private var subChunks: Array<JukeboxSubChunk?> = arrayOfNulls((fullHeight shr 4) + 1)
    private var height: ShortArray = ShortArray(16 * 16)
    private var chunkState: ChunkState = ChunkState.NEW
    private var dirty: Boolean = false
    private var changed: Boolean = false
    private var loaders: MutableList<ChunkLoader> = mutableListOf()
    private var writeLock: Lock
    private var readLock: Lock

    init {
        val lock: ReadWriteLock = ReentrantReadWriteLock()
        this.writeLock = lock.writeLock()
        this.readLock = lock.readLock()
    }

    companion object {
        const val CHUNK_VERSION = 40
    }

    override fun getWorld(): World {
        return this.world
    }

    override fun getDimension(): Dimension {
        return this.dimension
    }

    override fun getX(): Int {
        return this.chunkX
    }

    override fun getZ(): Int {
        return this.chunkZ
    }

    override fun getMinY(): Int {
        return this.minY
    }

    override fun getMaxY(): Int {
        return this.maxY
    }

    override fun isGenerated(): Boolean {
        return chunkState.ordinal >= 1
    }

    override fun isPopulated(): Boolean {
        return chunkState.ordinal >= 2
    }

    override fun isFinished(): Boolean {
        return chunkState.ordinal >= 3
    }

    fun getChunkState(): ChunkState {
        return this.chunkState
    }

    fun setChunkState(chunkState: ChunkState) {
        this.chunkState = chunkState
    }

    override fun getPlayers(): Set<JukeboxPlayer> {
        return this.players
    }

    override fun getEntities(): Set<JukeboxEntity> {
        return this.entities
    }

    override fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block {
        this.readLock.lock()
        return try {
            if (this.isHeightOutOfBounds(y)) {
                return JukeboxBlock.AIR
            }
            val subY: Int = this.getSubChunkIndex(y)
            if (this.subChunks[subY] == null) {
                this.subChunks[subY] = JukeboxSubChunk(y - (abs(this.minY) shr 4))
            }
            val block = this.subChunks[subY]!!.getBlock(x, y, z, layer).toJukeboxBlock()
            block.setLocation(Location(this.world, x, y, z, this.dimension))
            block.setLayer(layer)
            block
        } finally {
            this.readLock.unlock()
        }
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return this.getBlock(x, y, z, 0)
    }

    override fun getBlock(location: Vector, layer: Int): Block {
        return this.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer)
    }

    override fun getBlock(location: Vector): Block {
        return this.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0)
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block) {
        this.writeLock.lock()
        try {
            if (isHeightOutOfBounds(y)) return
            val subY = getSubChunkIndex(y)
            val subChunk: JukeboxSubChunk = this.getOrCreateSubChunk(subY)
            subChunk.setBlock(x, y, z, layer, block)
            this.dirty = true
            this.changed = true
        } finally {
            this.writeLock.unlock()
        }
    }

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        this.setBlock(x, y, z, 0, block)
    }

    override fun setBlock(location: Vector, layer: Int, block: Block) {
        this.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, block)
    }

    override fun setBlock(location: Vector, block: Block) {
        this.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, block)
    }

    override fun getBiome(x: Int, y: Int, z: Int): Biome {
        this.readLock.lock()
        try {
            if (isHeightOutOfBounds(y)) return Biome.PLAINS
            return this.getOrCreateSubChunk(getSubChunkIndex(y)).getBiome(x, y, z)
        } finally {
            this.readLock.unlock()
        }
    }

    override fun getBiome(location: Vector): Biome {
        return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ())
    }

    override fun setBiome(x: Int, y: Int, z: Int, biome: Biome) {
        this.writeLock.lock()
        try {
            if (isHeightOutOfBounds(y)) return
            this.getOrCreateSubChunk(getSubChunkIndex(y)).setBiome(x, y, z, biome)
            this.dirty = true
            this.changed = true
        } finally {
            this.writeLock.unlock()
        }
    }

    override fun setBiome(location: Vector, biome: Biome) {
        this.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome)
    }

    override fun getBlockEntities(): Collection<BlockEntity> {
        return this.blockEntities.values
    }

    override fun getBlockEntity(x: Int, y: Int, z: Int): BlockEntity? {
        return this.blockEntities[Utils.indexOf(x, y, z)]
    }

    override fun getBlockEntity(vector: Vector): BlockEntity? {
        return this.blockEntities[Utils.indexOf(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ())]
    }

    override fun setBlockEntity(x: Int, y: Int, z: Int, blockEntity: BlockEntity) {
        this.blockEntities.put(Utils.indexOf(x, y, z), blockEntity)
        this.dirty = true
        this.changed = true
    }

    override fun setBlockEntity(vector: Vector, blockEntity: BlockEntity) {
        this.blockEntities.put(Utils.indexOf(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()), blockEntity)
        this.dirty = true
        this.changed = true
    }

    override fun removeBlockEntity(x: Int, y: Int, z: Int) {
        this.blockEntities.remove(Utils.indexOf(x, y, z))
    }

    override fun removeBlockEntity(vector: Vector) {
        this.blockEntities.remove(Utils.indexOf(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ()))
    }

    fun getWriteLock(): Lock {
        return this.writeLock
    }

    fun isDirty(): Boolean {
        return this.dirty
    }

    fun setDirty(dirty: Boolean) {
        this.dirty = dirty
    }

    fun isChanged(): Boolean {
        return this.changed
    }

    fun setChanged(changed: Boolean) {
        this.changed = changed
    }

    fun getHeight(): ShortArray {
        return this.height
    }

    @Synchronized
    fun addLoader(chunkLoader: ChunkLoader) {
        loaders.add(chunkLoader)
    }

    @Synchronized
    fun removeLoader(chunkLoader: ChunkLoader) {
        loaders.remove(chunkLoader)
    }

    @Synchronized
    fun getLoaders(): Set<ChunkLoader> {
        return ImmutableSet.copyOf(loaders)
    }

    fun addEntity(entity: JukeboxEntity) {
        entities.add(entity)
        if (entity is JukeboxPlayer) {
            players.add(entity)
        }
    }

    fun removeEntity(entity: Entity) {
        entities.removeIf { it.getEntityId() == entity.getEntityId() }
        if (entity is Player) {
            players.removeIf { it.getEntityId() == entity.getEntityId() }
        }
    }

    private fun isHeightOutOfBounds(y: Int): Boolean {
        return y < this.minY || y > this.maxY
    }

    fun getSubChunkIndex(y: Int): Int {
        return (y shr 4) + (abs(this.minY) shr 4)
    }

    fun getOrCreateSubChunk(subChunkIndex: Int, lock: Boolean = false): JukeboxSubChunk {
        if (lock) this.writeLock.lock()
        return try {
            for (y in 0..subChunkIndex) {
                if (this.subChunks[y] == null) {
                    this.subChunks[y] = JukeboxSubChunk(y - (abs(this.minY) shr 4))
                }
            }
            this.subChunks[subChunkIndex]!!
        } finally {
            if (lock) this.writeLock.unlock()
        }
    }

    fun getSubChunk(subChunkIndex: Int): JukeboxSubChunk? {
        this.readLock.lock()
        try {
            return this.subChunks[subChunkIndex]
        } finally {
            this.readLock.unlock()
        }
    }

    fun getAvailableSubChunks(): Int {
        return this.sum(subChunks) { o -> if (o == null) 0 else 1 }
    }

    private fun writeTo(byteBuf: ByteBuf) {
        var lastBiomes: Palette<Biome> = Palette(Biome.PLAINS)
        for (subChunk in this.subChunks) {
            if (subChunk == null) continue
            subChunk.writeToNetwork(byteBuf)
        }

        for (subChunk in subChunks) {
            if (subChunk == null) {
                lastBiomes.writeToNetwork(byteBuf, BiomeIdSerializer(), lastBiomes)
                continue
            }
            subChunk.getBiomePalette().writeToNetwork(byteBuf, BiomeIdSerializer())
            lastBiomes = subChunk.getBiomePalette()
        }
        byteBuf.writeByte(0) // edu - border blocks

        val blockEntities = getBlockEntities()
        if (!blockEntities.isEmpty()) {
            try {
                NbtUtils.createNetworkWriter(ByteBufOutputStream(byteBuf)).use { writer ->
                    for (blockEntity in blockEntities) {
                        writer.writeTag(blockEntity.toJukeboxBlockEntity().toCompound().build())
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun createLevelChunkPacket(): LevelChunkPacket? {
        val byteBuf = Unpooled.buffer()
        return try {
            val levelChunkPacket = LevelChunkPacket()
            levelChunkPacket.chunkX = this.chunkX
            levelChunkPacket.chunkZ = this.chunkZ
            levelChunkPacket.isCachingEnabled = false
            levelChunkPacket.isRequestSubChunks = false
            levelChunkPacket.subChunksLength = this.getAvailableSubChunks()
            writeTo(byteBuf.retain())
            levelChunkPacket.data = byteBuf
            levelChunkPacket
        } finally {
            byteBuf.release()
        }
    }

    private fun <E> sum(array: Array<E>, mapper: Function<E, Int>): Int {
        var sum = 0
        for (e in array) sum += mapper.apply(e)
        return sum
    }
}