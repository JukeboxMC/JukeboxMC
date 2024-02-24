package org.jukeboxmc.api.scheduler

interface TaskHandler {

    fun cancel()

    fun getTaskId(): Int

    fun isAsync(): Boolean

    fun getTask(): Runnable

    fun isCancelled(): Boolean

    fun getDelay(): Int

    fun setDelay(value: Int)

    fun isDelayed(): Boolean

    fun getPeriod(): Int

    fun setPeriod(value: Int)

    fun isRepeating(): Boolean

    fun getLastRunTick(): Long

    fun getNextRunTick(): Long

    fun setNextRunTick(value: Long)
}