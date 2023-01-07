package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerKickEvent extends PlayerEvent implements Cancellable {

    private String reason;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerKickEvent( Player player, String reason ) {
        super( player );
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason( String reason ) {
        this.reason = reason;
    }
}
