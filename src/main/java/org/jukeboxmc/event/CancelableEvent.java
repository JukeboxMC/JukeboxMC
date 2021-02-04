package org.jukeboxmc.event;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class CancelableEvent extends Event {

    private boolean cancelled = false;

    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

    public void setCancelled() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
