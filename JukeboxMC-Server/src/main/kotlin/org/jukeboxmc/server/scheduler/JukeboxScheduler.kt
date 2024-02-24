package org.jukeboxmc.server.scheduler

import com.google.common.util.concurrent.ThreadFactoryBuilder
import org.jukeboxmc.api.scheduler.Scheduler
import org.jukeboxmc.api.scheduler.TaskHandler
import org.jukeboxmc.server.JukeboxServer
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class JukeboxScheduler(private val server: JukeboxServer = JukeboxServer.getInstance()) : Scheduler {

    private var threadedExecutor: ExecutorService? = null
    private var chunkExecutor: ExecutorService? = null

    private val currentId = AtomicInteger()
    private val pendingTasks = LinkedList<JukeboxTaskHandler>()
    private val taskHandlerMap: MutableMap<Int, JukeboxTaskHandler> = ConcurrentHashMap()
    private val assignedTasks: MutableMap<Long, LinkedList<JukeboxTaskHandler>> = ConcurrentHashMap()

    companion object {
        private var instance: JukeboxScheduler? = null

        fun setJukeboxServer(scheduler: JukeboxScheduler) {
            if (this.instance == null) {
                this.instance = scheduler
                return
            }
            throw UnsupportedOperationException("Cannot redefine singleton scheduler")
        }

        fun getInstance(): JukeboxScheduler {
            return instance!!
        }
    }

    init {
        setJukeboxServer(this)
        val builder = ThreadFactoryBuilder()
        builder.setNameFormat("JukeboxMC Scheduler Executor")
        this.threadedExecutor =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), builder.build())
        this.chunkExecutor =
            Executors.newSingleThreadExecutor(ThreadFactoryBuilder().setNameFormat("JukeboxMC Chunk Executor").build())
    }

    fun getChunkExecutor(): ExecutorService {
        return this.chunkExecutor!!
    }

    fun onTick(currentTick: Long) {
        var task: JukeboxTaskHandler?
        while (this.pendingTasks.poll().also { task = it } != null) {
            val tick = currentTick.coerceAtLeast(task!!.getNextRunTick())
            task?.let { this.assignedTasks.computeIfAbsent(tick) { LinkedList() }.add(it) }
        }
        val queued: LinkedList<JukeboxTaskHandler> = this.assignedTasks.remove(currentTick) ?: return
        for (taskHandler in queued) {
            runTask(taskHandler, currentTick)
        }
    }

    private fun runTask(taskHandler: JukeboxTaskHandler, currentTick: Long) {
        if (taskHandler.isCancelled()) {
            this.taskHandlerMap.remove(taskHandler.getTaskId())
            return
        }

        if (taskHandler.isAsync()) {
            this.threadedExecutor?.execute { taskHandler.onRun(currentTick) }
        } else {
            taskHandler.onRun(currentTick)
        }

        if (taskHandler.calculateNextTick(currentTick)) {
            this.pendingTasks.add(taskHandler)
            return
        }

        val removed = this.taskHandlerMap.remove(taskHandler.getTaskId()) ?: return
        removed.cancel()
    }

    private fun addTask(runnable: Runnable, delay: Int, period: Int, async: Boolean): TaskHandler {
        if (delay < 0 || period < 0) {
            throw RuntimeException("Attempted to register a task with negative delay or period!")
        }
        val currentTick = getCurrentTick()
        val taskId: Int = this.currentId.getAndIncrement()
        val handler = JukeboxTaskHandler(runnable, taskId, async)
        handler.setDelay(delay)
        handler.setPeriod(period)
        handler.setNextRunTick(if (handler.isDelayed()) currentTick + delay else currentTick)
        this.pendingTasks.add(handler)
        this.taskHandlerMap[taskId] = handler
        return handler
    }

    override fun execute(runnable: Runnable): TaskHandler {
        return this.addTask(runnable, 0, 0, false)
    }

    override fun executeAsync(runnable: Runnable): TaskHandler {
        return this.addTask(runnable, 0, 0, true)
    }

    override fun scheduleDelayed(runnable: Runnable, delay: Int): TaskHandler {
        return this.addTask(runnable, delay, 0, false)
    }

    override fun scheduleDelayedAsync(runnable: Runnable, delay: Int): TaskHandler {
        return this.addTask(runnable, delay, 0, true)
    }

    override fun scheduleRepeating(runnable: Runnable, period: Int): TaskHandler {
        return this.addTask(runnable, 0, period, false)
    }

    override fun scheduleRepeatingAsync(runnable: Runnable, period: Int): TaskHandler {
        return this.addTask(runnable, 0, period, true)
    }

    override fun scheduleDelayedRepeating(runnable: Runnable, delay: Int, period: Int): TaskHandler {
        return this.addTask(runnable, delay, period, false)
    }

    override fun scheduleDelayedRepeatingAsync(runnable: Runnable, delay: Int, period: Int): TaskHandler {
        return this.addTask(runnable, delay, period, false)
    }

    override fun getCurrentTick(): Long {
        return this.server.getCurrentTick()
    }

    override fun shutdown() {
        this.threadedExecutor?.let {
            it.shutdown()
            var count = 25
            while (!it.isTerminated && count-- > 0) {
                it.awaitTermination(100, TimeUnit.MILLISECONDS)
            }
        }
    }
}