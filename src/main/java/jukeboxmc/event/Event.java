package jukeboxmc.event;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Event {

    private boolean cancelled = false;

    public void setCancelled( boolean cancelled ) {
        if ( !( this instanceof Cancellable ) ) {
            throw new CancellableException();
        }

        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        if ( !( this instanceof Cancellable ) ) {
            throw new CancellableException();
        }

        return this.cancelled;
    }
}
