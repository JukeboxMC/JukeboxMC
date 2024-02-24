package org.jukeboxmc.api.scheduler

interface Scheduler {

    /**
     * Executes a task synchronously on the server thread
     *
     * @param runnable the code to execute synchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun execute(runnable: Runnable): TaskHandler

    /**
     * Executes a task asynchronously
     *
     * @param runnable the code to execute asynchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun executeAsync(runnable: Runnable): TaskHandler

    /**
     * Executes a task synchronously and with a certain delay
     *
     * @param runnable the code to execute synchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleDelayed(runnable: Runnable, delay: Int): TaskHandler

    /**
     * Executes a task asynchronously and with a certain delay
     *
     * @param runnable the code to execute asynchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleDelayedAsync(runnable: Runnable, delay: Int): TaskHandler

    /**
     * Executes a task synchronously and with a given period.
     * The code will be executed as long as the task runs
     *
     * @param runnable the code to execute synchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleRepeating(runnable: Runnable, period: Int): TaskHandler

    /**
     * Executes a task asynchronously and with a given period.
     * The code will be executed as long as the task runs
     *
     * @param runnable the code to execute asynchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleRepeatingAsync(runnable: Runnable, period: Int): TaskHandler

    /**
     * Executes a task synchronously, with a certain delay and period.
     * The code will be executed as long as the task runs
     *
     * @param runnable the code to execute synchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleDelayedRepeating(runnable: Runnable, delay: Int, period: Int): TaskHandler

    /**
     * Executes a task asynchronously, with a certain delay and period.
     * The code will be executed as long as the task runs
     *
     * @param runnable the code to execute asynchronously
     *
     * @return the task handler which is maintaining the task to execute
     */
    fun scheduleDelayedRepeatingAsync(runnable: Runnable, delay: Int, period: Int): TaskHandler

    /**
     * Retrieves the current tick of the server
     *
     * @return the current server tick
     */
    fun getCurrentTick(): Long

    /**
     * Terminates this task scheduler
     */
    fun shutdown()

}