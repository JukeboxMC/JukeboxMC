package org.jukeboxmc.api.event

interface Cancellable {

    /**
     * Updates the cancelled state to the given value
     *
     * @param cancelled which should be set
     */
    fun setCancelled(cancelled: Boolean)

    /**
     * Retrieves whether the [Cancellable] implementation is cancelled or not
     *
     * @return whether the event implementation is cancelled
     */
    fun isCancelled(): Boolean
}