package org.jukeboxmc.event;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface Cancellable {

    /**
     * Updates the cancelled state to the given value
     *
     * @param cancelled which should be set
     */
    void setCancelled( boolean cancelled );

    /**
     * Retrieves whether the {@link Cancellable} implementation is cancelled or not
     *
     * @return whether the event implementation is cancelled
     */
    boolean isCancelled();
}