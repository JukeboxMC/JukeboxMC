package org.jukeboxmc.event;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CancelableException extends Throwable {

    /**
     * Represents the constructor of this {@link CancelableException}
     */
    public CancelableException() {
        super("This Event is not cancellable!");
    }
}