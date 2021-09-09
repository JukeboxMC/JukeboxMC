package jukeboxmc.event;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CancellableException extends RuntimeException {

    /**
     * Represents the constructor of this {@link CancellableException}
     */
    public CancellableException() {
        super( "This Event is not cancellable!" );
    }
}