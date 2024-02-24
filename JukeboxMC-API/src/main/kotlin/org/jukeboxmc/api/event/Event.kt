package org.jukeboxmc.api.event

abstract class Event {
    private var cancelled = false

    open fun setCancelled(cancelled: Boolean) {
        if (this !is Cancellable) {
            throw CancellableException()
        }
        this.cancelled = cancelled
    }

    open fun isCancelled(): Boolean {
        if (this !is Cancellable) {
            throw CancellableException()
        }
        return cancelled
    }
}