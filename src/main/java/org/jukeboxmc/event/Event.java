package org.jukeboxmc.event;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Event {

    private boolean cancelled = false;

    public void setCancelled(boolean cancelled) {
        if (!(this instanceof Cancelable)) {
            try {
                throw new CancelableException();
            } catch (CancelableException e) {
                e.printStackTrace();
            }
        }

        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        if (!(this instanceof Cancelable)) {
            try {
                throw new CancelableException();
            } catch (CancelableException e) {
                e.printStackTrace();
            }
        }

        return this.cancelled;
    }
}
