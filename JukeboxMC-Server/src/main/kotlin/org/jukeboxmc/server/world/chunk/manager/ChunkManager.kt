package org.jukeboxmc.server.world.chunk.manager

import com.google.common.collect.ImmutableSet
import com.spotify.futures.CompletableFutures
import it.unimi.dsi.fastutil.longs.*
import org.jukeboxmc.api.event.world.ChunkLoadEvent
import org.jukeboxmc.api.event.world.ChunkUnloadEvent
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.scheduler.JukeboxScheduler
import org.jukeboxmc.server.util.Utils
import org.jukeboxmc.server.world.JukeboxWorld
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import kotlin.concurrent.Volatile
import kotlin.math.abs

class ChunkManager(
    private val world: JukeboxWorld,
    private val dimension: Dimension
) {

    private val chunks: Long2ObjectMap<LoadingChunk> = Long2ObjectOpenHashMap()
    private val chunkLoadedTimes: Long2LongMap = Long2LongOpenHashMap()
    private val chunkLastAccessTimes: Long2LongMap = Long2LongOpenHashMap()
    private val executor: Executor = JukeboxScheduler.getInstance().getChunkExecutor()

    companion object {
        private val COMPLETED_VOID_FUTURE = CompletableFuture.completedFuture<Void?>(null)
    }

    @Synchronized
    fun getLoadedChunks(): Set<Chunk> {
        val chunks = ImmutableSet.builder<Chunk>()
        for (loadingChunk in this.chunks.values) {
            val chunk = loadingChunk.getChunk()
            if (chunk != null) {
                chunks.add(chunk)
            }
        }
        return chunks.build()
    }

    @Synchronized
    fun getLoadedCount(): Int {
        return chunks.size
    }

    @Synchronized
    fun getLoadedChunk(key: Long): JukeboxChunk? {
        val chunk = chunks[key]
        return chunk?.getChunk()
    }

    @Synchronized
    fun getLoadedChunk(x: Int, z: Int): JukeboxChunk? {
        return getLoadedChunk(Utils.toLong(x, z))
    }

    fun getChunk(x: Int, z: Int): Chunk? {
        try {
            var chunk = this.getLoadedChunk(x, z)
            if (chunk == null) {
                chunk = this.getChunkFuture(x, z).join()
            }
            return chunk
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @Synchronized
    fun getChunkFuture(
        chunkX: Int,
        chunkZ: Int,
        generate: Boolean = true,
        populate: Boolean = true,
        finish: Boolean = true
    ): CompletableFuture<JukeboxChunk> {
        val chunkKey = Utils.toLong(chunkX, chunkZ)
        chunkLastAccessTimes.put(chunkKey, System.currentTimeMillis())
        val chunk = chunks.computeIfAbsent(chunkKey,
            Long2ObjectFunction { key: Long ->
                LoadingChunk(
                    this,
                    dimension,
                    key,
                    true
                )
            })
        if (finish) {
            chunk.finish()
        } else if (populate) {
            chunk.populate()
        } else if (generate) {
            chunk.generate()
        }
        JukeboxServer.getInstance().getPluginManager().callEvent(ChunkLoadEvent(world, chunk.getChunk()))
        return chunk.getFuture()
    }

    @Synchronized
    fun isChunkLoaded(hash: Long): Boolean {
        val chunk = chunks[hash]
        return chunk?.getChunk() != null
    }

    @Synchronized
    fun isChunkLoaded(x: Int, z: Int): Boolean {
        return this.isChunkLoaded(Utils.toLong(x, z))
    }

    @Synchronized
    fun unloadChunk(hash: Long): Boolean {
        return this.unloadChunk(hash, save = true, safe = true)
    }

    fun unloadChunk(chunk: Chunk): Boolean {
        return this.unloadChunk(chunk, true)
    }

    fun unloadChunk(chunk: Chunk, save: Boolean): Boolean {
        return this.unloadChunk(chunk, save, true)
    }

    fun unloadChunk(chunk: Chunk, save: Boolean, safe: Boolean): Boolean {
        return this.unloadChunk(Utils.toLong(chunk.getX(), chunk.getZ()), save, safe)
    }

    fun unloadChunk(chunkKey: Long, save: Boolean, safe: Boolean): Boolean {
        val loadedChunk = this.getLoadedChunk(chunkKey) ?: return false
        val chunkUnloadEvent = ChunkUnloadEvent(world, loadedChunk, save)
        JukeboxServer.getInstance().getPluginManager().callEvent(chunkUnloadEvent)
        if (chunkUnloadEvent.isCancelled()) return false
        val result = unloadChunk0(chunkKey, chunkUnloadEvent.saveChunk(), safe)
        if (result) {
            this.chunks.remove(chunkKey)
        }
        return result
    }

    @Synchronized
    private fun unloadChunk0(chunkKey: Long, save: Boolean, safe: Boolean): Boolean {
        val loadingChunk = chunks[chunkKey] ?: return false
        val chunk = loadingChunk.getChunk() ?: return false
        if (chunk.getLoaders().isNotEmpty()) {
            return false
        }
        if (save) {
            this.saveChunk(chunk)
        }
        if (safe && world.getChunkPlayers(chunk.getX(), chunk.getZ(), chunk.getDimension()).isNotEmpty()) {
            return false
        }
        chunkLastAccessTimes.remove(chunkKey)
        chunkLoadedTimes.remove(chunkKey)
        return true
    }

    @Synchronized
    fun saveChunks(): CompletableFuture<Void?>? {
        val futures: MutableList<CompletableFuture<*>> = ArrayList()
        for (loadingChunk in chunks.values) {
            val chunk: JukeboxChunk? = loadingChunk.getChunk()
            if (chunk != null) {
                futures.add(saveChunk(chunk))
            }
        }
        return CompletableFuture.allOf(*futures.toTypedArray<CompletableFuture<*>>())
    }

    fun saveChunk(chunk: JukeboxChunk): CompletableFuture<Void?> {
        return if (chunk.isDirty()) {
            world.getLevelDBStorage().saveChunk(chunk).exceptionally { _ ->
                JukeboxServer.getInstance().getLogger().info("Unable to save chunk")
                null
            }
        } else COMPLETED_VOID_FUTURE
    }

    @Synchronized
    fun tick() {
        if (chunks.isEmpty()) {
            return
        }
        val time = System.currentTimeMillis()

        // Spawn chunk
        val spawnX: Int = world.getSpawnLocation(this.dimension).getChunkX()
        val spawnZ: Int = world.getSpawnLocation(this.dimension).getChunkZ()
        val spawnRadius: Int = 4 //TODO UPDATE
        //val spawnRadius: Int = JukeboxServer.getInstance().getViewDistance()

        // Do chunk garbage collection
        val iterator = chunks.long2ObjectEntrySet().iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val chunkKey = entry.longKey
            val loadingChunk = entry.value
            val chunk = loadingChunk.getChunk()
                ?: continue  // Chunk hasn't loaded
            if (abs(chunk.getX() - spawnX) <= spawnRadius && abs(chunk.getZ() - spawnZ) <= spawnRadius || chunk.getLoaders().isNotEmpty()) {
                continue  // Spawn protection or is loaded
            }
            val loadedTime = chunkLoadedTimes[chunkKey]
            if (time - loadedTime <= TimeUnit.SECONDS.toMillis(30)) {
                continue
            }
            val lastAccessTime = chunkLastAccessTimes[chunkKey]
            if (time - lastAccessTime <= TimeUnit.SECONDS.toMillis(120)) {
                continue
            }
            val chunkUnloadEvent = ChunkUnloadEvent(world, chunk, true)
            JukeboxServer.getInstance().getPluginManager().callEvent(chunkUnloadEvent)
            if (chunkUnloadEvent.isCancelled()) {
                return
            }
            if (unloadChunk0(chunkKey, chunkUnloadEvent.saveChunk(), true)) {
                iterator.remove()
            }
        }
    }

    class LoadingChunk(
        private val chunkManager: ChunkManager,
        private val dimension: Dimension,
        private val key: Long,
        private val load: Boolean
    ) {
        private val x: Int = Utils.fromHashX(key)
        private val z: Int = Utils.fromHashZ(key)
        private var future: CompletableFuture<JukeboxChunk>
        private var chunk: JukeboxChunk? = null

        @Volatile
        var generationRunning = 0

        @Volatile
        var populationRunning = 0

        @Volatile
        var finishRunning = 0

        companion object {
            private val GENERATION_RUNNING_UPDATER: AtomicIntegerFieldUpdater<LoadingChunk> =
                AtomicIntegerFieldUpdater.newUpdater(LoadingChunk::class.java, "generationRunning")
            private val POPULATION_RUNNING_UPDATER: AtomicIntegerFieldUpdater<LoadingChunk> =
                AtomicIntegerFieldUpdater.newUpdater(LoadingChunk::class.java, "populationRunning")
            private val FINISH_RUNNING_UPDATER: AtomicIntegerFieldUpdater<LoadingChunk> =
                AtomicIntegerFieldUpdater.newUpdater(LoadingChunk::class.java, "finishRunning")
        }

        init {
            if (this.load) {
                this.future =
                    this.chunkManager.world.getLevelDBStorage()
                        .readChunk(JukeboxChunk(this.chunkManager.world, this.dimension, this.x, this.z))
                        .thenApply { chunk ->
                            chunk ?: JukeboxChunk(this.chunkManager.world, this.dimension, this.x, this.z)
                        }
                this.future.whenComplete { _, throwable ->
                    if (throwable != null) {
                        throwable.printStackTrace()
                        JukeboxServer.getInstance().getLogger().error("Unable to load chunk $x:$z")

                        synchronized(ChunkManager) {
                            this.chunkManager.chunks.remove(key)
                        }
                    } else {
                        val currentTime = System.currentTimeMillis()
                        synchronized(ChunkManager) {
                            this.chunkManager.chunkLoadedTimes[key] = currentTime
                        }
                    }
                }
            } else {
                this.future = CompletableFuture.completedFuture(
                    JukeboxChunk(
                        this.chunkManager.world,
                        this.dimension,
                        this.x,
                        this.z
                    )
                )
            }
            this.future.whenComplete { loadedChunk, th ->
                th?.printStackTrace()
                this.chunk = loadedChunk
            }
        }

        fun getFuture(): CompletableFuture<JukeboxChunk> {
            return this.future
        }

        fun getChunk(): JukeboxChunk? {
            if (this.chunk != null && this.chunk!!.isGenerated() && this.chunk!!.isPopulated() && this.chunk!!.isFinished()) {
                return this.chunk!!
            }
            return null
        }

        fun generate() {
            if ((this.chunk == null || !this.chunk!!.isGenerated()) && GENERATION_RUNNING_UPDATER.compareAndSet(
                    this,
                    0,
                    1
                )
            ) {
                this.future = this.future.thenApplyAsync(GenerationTask.INSTANCE, chunkManager.executor)
                this.future.thenRun { GENERATION_RUNNING_UPDATER.compareAndSet(this, 1, 0) }
            }
        }

        fun populate() {
            generate()
            if ((this.chunk == null || !this.chunk!!.isPopulated()) && POPULATION_RUNNING_UPDATER.compareAndSet(
                    this,
                    0,
                    1
                )
            ) {
                val chunksToLoad = ArrayList<CompletableFuture<JukeboxChunk>>(8)
                for (z in this.z - 1..this.z + 1) {
                    for (x in this.x - 1..this.x + 1) {
                        if (x == this.x && z == this.z) continue
                        chunksToLoad.add(this.chunkManager.getChunkFuture(x, z,
                            generate = true,
                            populate = false,
                            finish = false
                        ))
                    }
                }
                val aroundFuture = CompletableFutures.allAsList(chunksToLoad)

                this.future = this.future.thenCombineAsync(aroundFuture, PopulationTask.INSTANCE, chunkManager.executor)
                this.future.thenRun { POPULATION_RUNNING_UPDATER.compareAndSet(this, 1, 0) }
            }
        }

        fun finish() {
            populate()
            if ((this.chunk == null || !this.chunk!!.isFinished()) && FINISH_RUNNING_UPDATER.compareAndSet(
                    this,
                    0,
                    1
                )
            ) {
                val chunksToLoad = ArrayList<CompletableFuture<JukeboxChunk>>(8)
                for (z in this.z - 1..this.z + 1) {
                    for (x in this.x - 1..this.x + 1) {
                        if (x == this.x && z == this.z) continue
                        chunksToLoad.add(this.chunkManager.getChunkFuture(x, z,
                            generate = true,
                            populate = true,
                            finish = false
                        ))
                    }
                }
                val aroundFuture = CompletableFutures.allAsList(chunksToLoad)

                this.future = this.future.thenCombineAsync(aroundFuture, FinishingTask.INSTANCE, chunkManager.executor)
                this.future.thenRun { FINISH_RUNNING_UPDATER.compareAndSet(this, 1, 0) }
            }
        }

        private fun clear() {
            this.future = this.future.thenApply { loadedChunk ->
                GENERATION_RUNNING_UPDATER.set(this, 0)
                POPULATION_RUNNING_UPDATER.set(this, 0)
                FINISH_RUNNING_UPDATER.set(this, 0)
                loadedChunk
            }
        }
    }

}