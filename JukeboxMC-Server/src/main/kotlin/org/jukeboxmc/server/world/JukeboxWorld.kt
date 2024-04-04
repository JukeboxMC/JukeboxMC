package org.jukeboxmc.server.world

import com.google.common.collect.ImmutableSet
import org.apache.commons.math3.util.FastMath
import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.packet.*
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.*
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.api.world.gamerule.GameRule
import org.jukeboxmc.api.world.gamerule.GameRuleValue
import org.jukeboxmc.api.world.generator.Generator
import org.jukeboxmc.server.BlockUpdateList
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.BlockUpdateNormal
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.RuntimeBlockDefinition
import org.jukeboxmc.server.entity.JukeboxEntity
import org.jukeboxmc.server.entity.item.JukeboxEntityItem
import org.jukeboxmc.server.extensions.*
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import org.jukeboxmc.server.world.chunk.manager.ChunkManager
import org.jukeboxmc.server.world.storage.LevelDBStorage
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path
import kotlin.math.ceil
import kotlin.math.floor

class JukeboxWorld(
    name: String,
    generatorMap: Map<Dimension, String>,
    val server: JukeboxServer = JukeboxServer.getInstance()
) : World {

    private val entities: MutableMap<Long, JukeboxEntity> = Collections.synchronizedMap(mutableMapOf())
    private val chunkManagers: MutableMap<Dimension, ChunkManager> = mutableMapOf()
    private val dimensionGenerator: MutableMap<Dimension, ThreadLocal<Generator>> = mutableMapOf()
    private var levelDBStorage: LevelDBStorage

    private val blockUpdateNormals: Queue<BlockUpdateNormal> = ConcurrentLinkedQueue()
    private val blockUpdateList = BlockUpdateList()
    private val random: Random = Random()

    private var nextTimeSendTick: Long = 0

    init {
        for (dimension in Dimension.entries) {
            this.chunkManagers[dimension] = ChunkManager(this, dimension)
            this.dimensionGenerator[dimension] = ThreadLocal.withInitial {
                val generatorName: String = generatorMap[dimension] ?: this.server.getGeneratorName()
                this.server.createGenerator(
                    generatorName,
                    dimension
                )
            }
        }
        this.levelDBStorage = LevelDBStorage(this, Path("worlds/$name"))
    }

    fun tick(currentTick: Long) {
        if (!this.server.isRunnung()) return
        this.levelDBStorage.getWorldData()?.let {
            it.currentTick++
        }

        if (currentTick % 100 == 0L) {
            for (chunkManager in chunkManagers.values) {
                chunkManager.tick()
            }
        }

        if (this.getGameRule(GameRule.DO_DAYLIGHT_CYCLE)) {
            this.levelDBStorage.getWorldData()?.let {
                if (it.time == Int.MAX_VALUE) return
                it.time = it.time.plus(1)
            }
        }

        if (currentTick >= this.nextTimeSendTick) {
            this.levelDBStorage.getWorldData()?.let {
                SetTimePacket().apply {
                    this.time = it.time
                    sendWorldPacket(this)
                }
            }
            this.nextTimeSendTick = currentTick + 12 * 20
        }

        val entitiesCopy = ArrayList(this.entities.values)
        if (entitiesCopy.isNotEmpty()) {
            for (entity in entitiesCopy) {
                entity.tick(currentTick)
            }
        }

        while (!this.blockUpdateNormals.isEmpty()) {
            val updateNormal = this.blockUpdateNormals.poll()
            updateNormal.getBlock().toJukeboxBlock().onUpdate(UpdateReason.NORMAL)
        }

        while (this.blockUpdateList.getNextTaskTime() < currentTick) {
            val nextBlock: Block = this.blockUpdateList.getNextElement() ?: break
            val block = this.getBlock(nextBlock.getLocation(), nextBlock.getLayer()).toJukeboxBlock()
            val nextTime: Long = block.onUpdate(UpdateReason.SCHEDULED)
            if (nextTime > currentTick) {
                this.blockUpdateList.addElement(nextTime, block)
            }
        }
    }

    override fun getWorldData(): WorldData {
        return this.levelDBStorage.getWorldData()!!
    }

    override fun getCurrentTick(): Long {
        return this.levelDBStorage.getWorldData()?.currentTick ?: 0
    }

    override fun getName(): String {
        return this.getWorldData().levelName
    }

    override fun setName(name: String) {
        this.levelDBStorage.getWorldData()?.let { it.levelName = name }
    }

    override fun getTime(): Int {
        return this.levelDBStorage.getWorldData()?.time ?: 0
    }

    override fun setTime(time: Int) {
        this.levelDBStorage.getWorldData()?.let {
            it.time = time
            SetTimePacket().apply {
                this.time = it.time
                sendWorldPacket(this)
            }
        }
    }

    override fun getDifficulty(): Difficulty {
        return this.getWorldData().difficulty
    }

    override fun setDifficulty(difficulty: Difficulty) {
        this.getWorldData().difficulty = difficulty
    }

    override fun getGameRules(): List<GameRuleValue> {
        return this.getWorldData().gameRuleData
    }

    override fun <V> getGameRule(gameRule: GameRule): V {
        return this.getWorldData().gameRuleData.first { it.identifier.equals(gameRule.identifier, true) }.value as V
    }

    override fun setGameRule(gameRule: GameRule, value: Any) {
        this.getGameRules().first { it.identifier.equals(gameRule.identifier, true) }.value = value
        GameRulesChangedPacket().apply {
            this.gameRules.addAll(this@JukeboxWorld.getGameRules().map { it.toNetwork() }.toList())
            this@JukeboxWorld.getPlayers().forEach { it.toJukeboxPlayer().sendPacket(this) }
        }
    }

    override fun getBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension): Block {
        val chunk = getLoadedChunk(x shr 4, z shr 4, dimension)?.toJukeboxChunk() ?: return JukeboxBlock.AIR
        return chunk.getBlock(x, y, z, layer)
    }

    override fun getBlock(x: Int, y: Int, z: Int, layer: Int): Block {
        return this.getBlock(x, y, z, layer, Dimension.OVERWORLD)
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return this.getBlock(x, y, z, 0, Dimension.OVERWORLD)
    }

    override fun getBlock(location: Vector, layer: Int, dimension: Dimension): Block {
        return this.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, dimension)
    }

    override fun getBlock(location: Vector, layer: Int): Block {
        return this.getBlock(location, layer, Dimension.OVERWORLD)
    }

    override fun getBlock(location: Vector): Block {
        return this.getBlock(location, 0, Dimension.OVERWORLD)
    }

    override fun getHighestBlockYAt(x: Int, z: Int, dimension: Dimension): Int {
        for (y in dimension.getMaxY() downTo dimension.getMinY()) {
            if (this.getBlock(x, y, z).getType() != BlockType.AIR) {
                return y
            }
        }
        return dimension.getMinY()
    }

    override fun getHighestBlockYAt(x: Int, z: Int): Int  {
        return this.getHighestBlockYAt(x, z, Dimension.OVERWORLD)
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension, block: Block, update: Boolean) {
        val jukeboxBlock = block.toJukeboxBlock()
        val chunk: JukeboxChunk? = getLoadedChunk(x shr 4, z shr 4, dimension)?.toJukeboxChunk()

        chunk?.setBlock(x, y, z, layer, jukeboxBlock)
        chunk?.setDirty(true)

        jukeboxBlock.setLocation(Location(this, x, y, z, dimension))
        jukeboxBlock.setLayer(layer)

        val updateBlockPacket = UpdateBlockPacket()
        updateBlockPacket.blockPosition = Vector(x, y, z).toVector3i()
        updateBlockPacket.definition = RuntimeBlockDefinition(block.getNetworkId())
        updateBlockPacket.dataLayer = layer
        updateBlockPacket.flags.addAll(UpdateBlockPacket.FLAG_ALL_PRIORITY)
        if (chunk != null) {
            this.sendChunkPacket(chunk.getX(), chunk.getZ(), updateBlockPacket)
        }

        if (update) {
            jukeboxBlock.onUpdate(UpdateReason.NORMAL)
            this.getBlock(x, y, z, layer, dimension).toJukeboxBlock().onUpdate(UpdateReason.NORMAL)
            this.updateBlockAround(x, y, z)

            for (blockFace: BlockFace in BlockFace.entries) {
                val blockSide = block.getRelative(blockFace).toJukeboxBlock()
                val next = blockSide.onUpdate(UpdateReason.NEIGHBORS)
                if (next > this.server.getCurrentTick()) {
                    this.scheduleBlockUpdate(blockSide, next)
                }
            }
        }
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, dimension: Dimension, block: Block) {
        this.setBlock(x, y, z, layer, dimension, block, true)
    }

    override fun setBlock(x: Int, y: Int, z: Int, layer: Int, block: Block) {
        this.setBlock(x, y, z, layer, Dimension.OVERWORLD, block)
    }

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        this.setBlock(x, y, z, 0, Dimension.OVERWORLD, block)
    }

    override fun setBlock(location: Vector, layer: Int, dimension: Dimension, block: Block, update: Boolean) {
        this.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), layer, dimension, block, update)
    }

    override fun setBlock(location: Vector, layer: Int, dimension: Dimension, block: Block) {
        this.setBlock(location, layer, dimension, block, true)
    }

    override fun setBlock(location: Vector, layer: Int, block: Block) {
        this.setBlock(location, layer, Dimension.OVERWORLD, block, true)
    }

    override fun setBlock(location: Vector, block: Block) {
        this.setBlock(location, 0, Dimension.OVERWORLD, block, true)
    }

    override fun getBiome(x: Int, y: Int, z: Int, dimension: Dimension): Biome {
        val chunk = this.getLoadedChunk(x shr 4, z shr 4, dimension) ?: return Biome.PLAINS
        return chunk.getBiome(x, y, z)
    }

    override fun getBiome(x: Int, y: Int, z: Int): Biome {
        return this.getBiome(x, y, z, Dimension.OVERWORLD)
    }

    override fun getBiome(location: Vector): Biome {
        return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), Dimension.OVERWORLD)
    }

    override fun getBiome(location: Vector, dimension: Dimension): Biome {
        return this.getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), dimension)
    }

    override fun setBiome(x: Int, y: Int, z: Int, dimension: Dimension, biome: Biome) {
        val chunk = this.getLoadedChunk(x shr 4, z shr 4, dimension) ?: return
        chunk.setBiome(x, y, z, biome)
    }

    override fun setBiome(x: Int, y: Int, z: Int, biome: Biome) {
        this.setBiome(x, y, z, Dimension.OVERWORLD, biome)
    }

    override fun setBiome(location: Vector, dimension: Dimension, biome: Biome) {
        this.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), dimension, biome)
    }

    override fun setBiome(location: Vector, biome: Biome) {
        this.setBiome(location, Dimension.OVERWORLD, biome)
    }

    override fun getBlockEntities(dimension: Dimension): Collection<BlockEntity> {
        val blockEntities: MutableSet<BlockEntity> = LinkedHashSet()
        this.chunkManagers[dimension]?.let { it ->
            it.getLoadedChunks().forEach {
                blockEntities.addAll(it.getBlockEntities())
            }
        }
        return blockEntities
    }

    override fun getBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension): BlockEntity? {
        val chunk = this.getLoadedChunk(x shr 4, z shr 4, dimension) ?: return null
        return chunk.getBlockEntity(x, y, z)
    }

    override fun getBlockEntity(location: Vector): BlockEntity? {
        val chunk =
            this.getLoadedChunk(location.getChunkX(), location.getChunkZ(), location.getDimension()) ?: return null
        return chunk.getBlockEntity(location)
    }

    override fun setBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension, blockEntity: BlockEntity) {
        val chunk = this.getLoadedChunk(x shr 4, z shr 4, dimension) ?: return
        chunk.setBlockEntity(x, y, z, blockEntity)
    }

    override fun setBlockEntity(location: Vector, blockEntity: BlockEntity) {
        val chunk = this.getLoadedChunk(location.getChunkX(), location.getChunkZ(), location.getDimension()) ?: return
        chunk.setBlockEntity(location, blockEntity)
    }

    override fun removeBlockEntity(x: Int, y: Int, z: Int, dimension: Dimension) {
        val chunk = this.getLoadedChunk(x shr 4, z shr 4, dimension) ?: return
        chunk.removeBlockEntity(x, y, z)
    }

    override fun removeBlockEntity(location: Vector) {
        val chunk = this.getLoadedChunk(location.getChunkX(), location.getChunkZ(), location.getDimension()) ?: return
        chunk.removeBlockEntity(location)
    }

    override fun isChunkLoaded(x: Int, z: Int, dimension: Dimension): Boolean {
        return this.chunkManagers[dimension]?.isChunkLoaded(x, z) ?: false
    }

    override fun getChunk(x: Int, z: Int, dimension: Dimension): Chunk? {
        return this.chunkManagers[dimension]?.getChunk(x, z)
    }

    override fun getLoadedChunk(x: Int, z: Int, dimension: Dimension): Chunk? {
        return this.chunkManagers[dimension]?.getLoadedChunk(x, z)
    }

    fun getLoadedChunk(hash: Long, dimension: Dimension): Chunk? {
        return this.chunkManagers[dimension]?.getLoadedChunk(hash)
    }

    override fun getLoadedChunks(dimension: Dimension): Set<Chunk> {
        return this.chunkManagers[dimension]?.getLoadedChunks()!!
    }

    fun getChunkFuture(chunkX: Int, chunkZ: Int, dimension: Dimension?): CompletableFuture<JukeboxChunk> {
        return chunkManagers[dimension]!!.getChunkFuture(chunkX, chunkZ)
    }

    override fun save() {
        this.levelDBStorage.saveWorldData()
        this.chunkManagers.forEach { (_, value) -> value.saveChunks()?.join() }
    }

    override fun getChunkPlayers(x: Int, z: Int, dimension: Dimension): Collection<Player> {
        val chunk = getLoadedChunk(x, z, dimension)
        return chunk?.getPlayers() ?: ImmutableSet.of()
    }

    fun getLoadedChunkEntities(hash: Long, dimension: Dimension): MutableSet<Entity> {
        this.getLoadedChunk(hash, dimension)?.let {
            val entitySet = mutableSetOf<Entity>()
            entitySet.addAll(it.getEntities())
            return entitySet
        }
        return mutableSetOf()
    }

    fun getLoadedChunkEntities(chunkX: Int, chunkZ: Int, dimension: Dimension): MutableSet<Entity> {
        this.getLoadedChunk(chunkX, chunkZ, dimension)?.let {
            val entitySet = mutableSetOf<Entity>()
            entitySet.addAll(it.getEntities())
            entitySet.addAll(it.getPlayers())
            return entitySet
        }
        return mutableSetOf()
    }

    override fun getGenerator(dimension: Dimension): Generator {
        return this.dimensionGenerator[dimension]?.get()!!
    }

    override fun getSpawnLocation(dimension: Dimension): Location {
        val spawnLoc = this.getGenerator(dimension).spawnLocation()
        return Location(this, spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ(), dimension)
    }

    override fun close() {
        this.levelDBStorage.close()
    }

    fun getLevelDBStorage(): LevelDBStorage {
        return this.levelDBStorage
    }

    fun addEntity(entity: JukeboxEntity) {
        this.entities[entity.getEntityId()] = entity
    }

    fun removeEntity(entity: Entity) {
        this.entities.remove(entity.getEntityId())
    }

    override fun getEntityById(entityId: Long): Entity? {
        return this.entities[entityId]
    }

    override fun getPlayers(): Collection<Player> {
        return this.entities.values.filter { it is Player }.map { it as Player }.toList()
    }

    override fun getEntities(): Collection<Entity> {
        return this.entities.values
    }

    override fun spawnParticle(player: Player?, particle: Particle, location: Vector, data: Int) {
        val levelEventPacket = LevelEventPacket()
        levelEventPacket.type = ParticleRegistry.getLevelEvent(particle)
        levelEventPacket.data = data
        levelEventPacket.position = location.toVector3f()

        if (player == null) {
            this.sendChunkPacket(location.getChunkX(), location.getChunkZ(), levelEventPacket)
        } else {
            player.toJukeboxPlayer().sendPacket(levelEventPacket)
        }
    }

    override fun spawnParticle(player: Player?, particle: Particle, location: Vector) {
        this.spawnParticle(player, particle, location, 0)
    }

    override fun spawnParticle(particle: Particle, location: Vector, data: Int) {
        val levelEventPacket = LevelEventPacket()
        levelEventPacket.type = ParticleRegistry.getLevelEvent(particle)
        levelEventPacket.data = data
        levelEventPacket.position = location.toVector3f()
        this.sendChunkPacket(location.getChunkX(), location.getChunkZ(), levelEventPacket)
    }

    override fun spawnParticle(particle: Particle, location: Vector) {
        this.spawnParticle(particle, location, 0)
    }

    override fun getNearbyEntities(
        boundingBox: AxisAlignedBB,
        dimension: Dimension,
        entity: Entity?
    ): Collection<Entity> {
        val targetEntity: MutableSet<Entity> = HashSet()

        val minX = FastMath.floor(((boundingBox.getMinX() - 2) / 16).toDouble()).toInt()
        val maxX = FastMath.ceil(((boundingBox.getMaxX() + 2) / 16).toDouble()).toInt()
        val minZ = FastMath.floor(((boundingBox.getMinZ() - 2) / 16).toDouble()).toInt()
        val maxZ = FastMath.ceil(((boundingBox.getMaxZ() + 2) / 16).toDouble()).toInt()

        for (x in minX..maxX) {
            for (z in minZ..maxZ) {
                val chunk = this.getLoadedChunk(x, z, dimension)
                if (chunk != null) {
                    for (iterateEntities in chunk.getEntities()) {
                        if (iterateEntities != entity) {
                            val bb = iterateEntities.getBoundingBox()
                            if (bb.intersectsWith(boundingBox)) {
                                if (entity != null) {
                                    if ((entity as JukeboxEntity).canCollideWith(iterateEntities)) {
                                        targetEntity.add(iterateEntities)
                                    }
                                } else {
                                    targetEntity.add(iterateEntities)
                                }
                            }
                        }
                    }
                }
            }
        }
        return targetEntity
    }

    override fun getCollisionCubes(
        boundingBox: AxisAlignedBB,
        entity: Entity,
    ): Collection<AxisAlignedBB> {
        val minX = FastMath.floor(boundingBox.getMinX().toDouble()).toInt()
        val minY = FastMath.floor(boundingBox.getMinY().toDouble()).toInt()
        val minZ = FastMath.floor(boundingBox.getMinZ().toDouble()).toInt()
        val maxX = FastMath.ceil(boundingBox.getMaxX().toDouble()).toInt()
        val maxY = FastMath.ceil(boundingBox.getMaxY().toDouble()).toInt()
        val maxZ = FastMath.ceil(boundingBox.getMaxZ().toDouble()).toInt()

        val collides: MutableList<AxisAlignedBB> = mutableListOf()

        for (z in minZ..maxZ) {
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    val block = this.getBlock(Vector(x, y, z), 0)
                    if (!block.canPassThrough() && block.getBoundingBox().intersectsWith(boundingBox)) {
                        collides.add(block.getBoundingBox())
                    }
                }
            }
        }
        return collides
    }

    override fun dropItem(location: Location, item: Item) {
        val entityItem = JukeboxEntityItem()
        entityItem.setLocation(location.clone())
        entityItem.setItem(item)
        entityItem.setVelocity(
            Vector(
                Math.random().toFloat() * 0.2F - 0.1f,
                0.2f,
                Math.random().toFloat() * 0.2F - 0.1f,
            )
        )
        entityItem.setPickupDelay(1, TimeUnit.SECONDS)
        entityItem.spawn()
    }

    override fun dropItemNaturally(location: Location, item: Item) {
        var loc = location
        val world = loc.getWorld().toJukeboxWorld()
        val xs: Float = world.random.nextFloat() * 0.7f - 0.35f
        val ys: Float = world.random.nextFloat() * 0.7f - 0.35f
        val zs: Float = world.random.nextFloat() * 0.7f - 0.35f
        loc = loc.clone()
        this.randomLocationWithinBlock(loc, xs, ys, zs)
        this.dropItem(loc, item)
    }

    private fun randomLocationWithinBlock(loc: Location, xs: Float, ys: Float, zs: Float) {
        val prevX = loc.getX().toDouble()
        val prevY = loc.getY().toDouble()
        val prevZ = loc.getZ().toDouble()
        loc.add(xs, ys, zs)
        if (loc.getX() < floor(prevX)) {
            loc.setX(floor(prevX).toFloat())
        }
        if (loc.getX() >= ceil(prevX)) {
            loc.setX(ceil(prevX - 0.01).toFloat())
        }
        if (loc.getY() < floor(prevY)) {
            loc.setY(floor(prevY).toFloat())
        }
        if (loc.getY() >= ceil(prevY)) {
            loc.setY(ceil(prevY - 0.01).toFloat())
        }
        if (loc.getZ() < floor(prevZ)) {
            loc.setZ(floor(prevZ).toFloat())
        }
        if (loc.getZ() >= ceil(prevZ)) {
            loc.setZ(ceil(prevZ - 0.01).toFloat())
        }
    }

    fun playLevelSound(
        location: Vector,
        soundEvent: SoundEvent,
        data: Int = -1,
        identifier: String = ":",
        baby: Boolean = false,
        relativeVolumeDisabled: Boolean = false
    ) {
        val levelSoundEventPacket = LevelSoundEventPacket()
        levelSoundEventPacket.position = location.toVector3f()
        levelSoundEventPacket.sound = soundEvent
        levelSoundEventPacket.extraData = data
        levelSoundEventPacket.identifier = identifier
        levelSoundEventPacket.isBabySound = baby
        levelSoundEventPacket.isRelativeVolumeDisabled = relativeVolumeDisabled
        this.sendChunkPacket(location.getChunkX(), location.getChunkZ(), levelSoundEventPacket)
    }

    fun playLevelSound2(
        location: Vector,
        soundEvent: SoundEvent,
        data: Int = -1,
        identifier: String = ":",
        baby: Boolean = false,
        relativeVolumeDisabled: Boolean = false
    ) {
        val levelSoundEventPacket = LevelSoundEvent2Packet()
        levelSoundEventPacket.position = location.toVector3f()
        levelSoundEventPacket.sound = soundEvent
        levelSoundEventPacket.extraData = data
        levelSoundEventPacket.identifier = identifier
        levelSoundEventPacket.isBabySound = baby
        levelSoundEventPacket.isRelativeVolumeDisabled = relativeVolumeDisabled
        this.sendChunkPacket(location.getChunkX(), location.getChunkZ(), levelSoundEventPacket)
    }

    fun sendLevelEvent(
        location: Vector,
        levelEvent: LevelEvent,
        data: Int = 0
    ) {
        val levelEventPacket = LevelEventPacket()
        levelEventPacket.position = location.toVector3f()
        levelEventPacket.type = levelEvent
        levelEventPacket.data = data
        this.sendChunkPacket(location.getChunkX(), location.getChunkZ(), levelEventPacket)
    }

    fun sendChunkPacket(x: Int, z: Int, packet: BedrockPacket) {
        for (entity in this.getPlayers()) {
            if (entity is JukeboxPlayer) {
                if (entity.isChunkLoaded(x, z)) {
                    entity.sendPacket(packet)
                }
            }
        }
    }

    fun sendDimensionPacket(dimension: Dimension, packet: BedrockPacket) {
        this.getPlayers().filter { it.getDimension() == dimension }.forEach { it.toJukeboxPlayer().sendPacket(packet) }
    }

    fun sendWorldPacket(packet: BedrockPacket) {
        this.getPlayers().forEach { it.toJukeboxPlayer().sendPacket(packet) }
    }

    fun updateBlockAround(x: Int, y: Int, z: Int) {
        val block = this.getBlock(x, y, z)
        for (blockFace in BlockFace.entries) {
            val blockLayer0: Block = block.getRelative(blockFace, 0)
            val blockLayer1: Block = block.getRelative(blockFace, 1)
            this.blockUpdateNormals.add(BlockUpdateNormal(blockLayer0, blockFace))
            this.blockUpdateNormals.add(BlockUpdateNormal(blockLayer1, blockFace))
        }
    }

    fun scheduleBlockUpdate(block: Block, delay: Long) {
        this.blockUpdateList.addElement(this.server.getCurrentTick() + delay, block)
    }

}