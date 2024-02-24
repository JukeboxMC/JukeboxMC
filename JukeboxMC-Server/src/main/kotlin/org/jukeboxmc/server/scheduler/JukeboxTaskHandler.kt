package org.jukeboxmc.server.scheduler

import org.jukeboxmc.api.scheduler.TaskHandler

class JukeboxTaskHandler(
    private val task: Runnable,
    private val taskId: Int,
    private val async: Boolean
) : TaskHandler {

    private var delay = 0
    private var period = 0

    private var lastRunTick: Long = 0
    private var nextRunTick: Long = 0

    private var cancelled = false

    fun onRun(currentTick: Long) {
        this.lastRunTick = currentTick
        try {
            this.task.run()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun calculateNextTick(currentTick: Long): Boolean {
        if (this.isCancelled() || !this.isRepeating()) {
            return false
        }
        this.nextRunTick = currentTick + period
        return true
    }

    override fun cancel() {
        if (this.cancelled) {
            return
        }
        this.cancelled = true
    }

    override fun getTaskId(): Int {
        return this.taskId
    }

    override fun isAsync(): Boolean {
        return this.async
    }

    override fun getTask(): Runnable {
        return this.task
    }

    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    override fun getDelay(): Int {
        return this.delay
    }

    override fun setDelay(value: Int) {
        this.delay = value
    }

    override fun isDelayed(): Boolean {
        return this.delay > 0
    }

    override fun getPeriod(): Int {
        return this.period
    }

    override fun setPeriod(value: Int) {
        this.period = value
    }

    override fun isRepeating(): Boolean {
        return this.period > 0
    }

    override fun getLastRunTick(): Long {
        return this.lastRunTick
    }

    override fun getNextRunTick(): Long {
        return this.nextRunTick
    }

    override fun setNextRunTick(value: Long) {
        this.nextRunTick = value
    }
}