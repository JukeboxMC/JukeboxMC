package org.jukeboxmc.server.player

import it.unimi.dsi.fastutil.longs.*
import org.cloudburstmc.protocol.bedrock.packet.ChunkRadiusUpdatedPacket
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket
import org.cloudburstmc.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxChunk
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.scheduler.JukeboxScheduler
import org.jukeboxmc.server.util.Utils
import org.jukeboxmc.server.world.chunk.ChunkComparator
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import java.util.concurrent.atomic.AtomicLong
import java.util.function.LongConsumer

class PlayerChunkManager(
    private val player: JukeboxPlayer
) {

    private val chunkComparator: ChunkComparator = ChunkComparator(this.player)
    private val loadedChunks: LongSet = LongOpenHashSet()
    private val sendQueue: Long2ObjectOpenHashMap<LevelChunkPacket> = Long2ObjectOpenHashMap()
    private val chunksSentCounter = AtomicLong()
    private var removeChunkLoader: LongConsumer? = null

    @Volatile
    private var radius = 0

    init {
        this.removeChunkLoader = LongConsumer { chunkKey: Long ->
            val chunk = this.player.getWorld().getLoadedChunk(chunkKey, player.getDimension())?: return@LongConsumer
            chunk.toJukeboxChunk().removeLoader(this.player)
            chunk.getEntities().forEach { it.despawn(this.player) }
        }
    }

    fun getChunkRadius(): Int {
        return radius shr 4
    }

    fun setChunkRadius(chunkRadius: Int) {
        var radius = chunkRadius
        radius = Utils.clamp(radius, 8, JukeboxServer.getInstance().getViewDistance())
        this.setRadius(radius shl 4)
    }

    fun setRadius(radius: Int) {
        if (this.radius != radius) {
            this.radius = radius
            val chunkRadiusUpdatePacket = ChunkRadiusUpdatedPacket()
            chunkRadiusUpdatePacket.radius = radius shr 4
            this.player.sendPacket(chunkRadiusUpdatePacket)
            this.queueNewChunks()
        }
    }

    @Synchronized
    fun sendQueued() {
        var chunksPerTick = 4
        val sendQueueIterator = sendQueue.long2ObjectEntrySet().iterator()
        // Remove chunks which are out of range
        while (sendQueueIterator.hasNext()) {
            val entry = sendQueueIterator.next()
            val key = entry.longKey
            if (!this.loadedChunks.contains(key)) {
                sendQueueIterator.remove()
                val chunk: Chunk? = this.player.getWorld().getLoadedChunk(key, this.player.getDimension())
                chunk?.toJukeboxChunk()?.removeLoader(this.player)
            }
        }
        val list: LongList = LongArrayList(this.sendQueue.keys)
        list.unstableSort(this.chunkComparator)
        for (key in list.toLongArray()) {
            if (chunksPerTick < 0) {
                break
            }
            val packet = sendQueue[key] ?:break
            this.sendQueue.remove(key)
            this.player.sendPacket(packet)

            this.player.getWorld().getLoadedChunkEntities(key, this.player.getDimension()).forEach {
                if (it != this.player && !it.isClosed() && !it.isDead()) {
                    it.spawn(this.player)
                }
            }
            this.chunksSentCounter.incrementAndGet()
            chunksPerTick--
        }
    }

    fun queueNewChunks() {
        this.queueNewChunks(this.player.getLocation())
    }

    fun queueNewChunks(location: Vector) {
        this.queueNewChunks(location.getChunkX(), location.getChunkZ())
    }

    @Synchronized
    fun queueNewChunks(chunkX: Int, chunkZ: Int) {
        val radius = this.getChunkRadius()
        val radiusSqr = radius * radius
        val chunksForRadius: LongSet = LongOpenHashSet()
        val sentCopy: LongSet = LongOpenHashSet(this.loadedChunks)
        val chunksToLoad: LongList = LongArrayList()
        for (x in -radius..radius) {
            for (z in -radius..radius) {
                if (x * x + z * z > radiusSqr) {
                    continue
                }
                val cx = chunkX + x
                val cz = chunkZ + z
                val key = Utils.toLong(cx, cz)
                chunksForRadius.add(key)
                if (this.loadedChunks.add(key)) {
                    chunksToLoad.add(key)
                }
            }
        }
        val loadedChunksChanged = this.loadedChunks.retainAll(chunksForRadius)
        if (loadedChunksChanged || !chunksToLoad.isEmpty()) {
            val packet = NetworkChunkPublisherUpdatePacket()
            packet.position = this.player.getLocation().toVector3i()
            packet.radius = this.radius
            this.player.sendPacket(packet)
        }

        // Order chunks for smoother loading
        chunksToLoad.sort(this.chunkComparator)
        for (key in chunksToLoad.toLongArray()) {
            val cx = Utils.fromHashX(key)
            val cz = Utils.fromHashZ(key)
            if (this.sendQueue.putIfAbsent(key, null) == null) {
                this.player.getWorld().getChunkFuture(cx, cz, this.player.getDimension()).thenApply { chunk ->
                    chunk.addLoader(player)
                    chunk
                }.thenApplyAsync(
                    JukeboxChunk::createLevelChunkPacket,
                    JukeboxScheduler.getInstance().getChunkExecutor()
                )
                    .whenComplete { packet, throwable ->
                        synchronized(this@PlayerChunkManager) {
                            if (throwable != null) {
                                throwable.printStackTrace()
                                if (this.sendQueue.remove(key) == null) {
                                    this.loadedChunks.remove(key)
                                }
                            } else if (!this.sendQueue.replace(key, null, packet)) {
                                if (this.sendQueue.containsKey(key)) {
                                    JukeboxServer.getInstance().getLogger().warn(
                                        ("Chunk (" + cx + "," + cz + ") already loaded for "
                                                + this.player.getName()) + ", values " + this.sendQueue[key]
                                    )
                                }
                            }
                        }
                    }
            }
        }
        sentCopy.removeAll(chunksForRadius)
        // Remove player from chunk loaders
        sentCopy.forEach {
            this.removeChunkLoader?.accept(it)
        }
    }

    fun isChunkInView(x: Int, z: Int): Boolean {
        return this.isChunkInView(Utils.toLong(x, z))
    }

    @Synchronized
    fun isChunkInView(key: Long): Boolean {
        return this.loadedChunks.contains(key)
    }

    fun getChunksSent(): Long {
        return this.chunksSentCounter.get()
    }

    fun getLoadedChunks(): LongSet? {
        return LongSets.unmodifiable(this.loadedChunks)
    }

    @Synchronized
    fun resendChunk(chunkX: Int, chunkZ: Int) {
        val chunkKey = Utils.toLong(chunkX, chunkZ)
        this.loadedChunks.remove(chunkKey)
        this.removeChunkLoader!!.accept(chunkKey)
    }

    fun prepareRegion(pos: Vector) {
        this.prepareRegion(pos.getChunkX(), pos.getChunkZ())
    }

    fun prepareRegion(chunkX: Int, chunkZ: Int) {
        this.clear()
        this.queueNewChunks(chunkX, chunkZ)
    }

    @Synchronized
    fun clear() {
        this.sendQueue.clear()
        this.loadedChunks.forEach(this.removeChunkLoader)
        this.loadedChunks.clear()
    }

}