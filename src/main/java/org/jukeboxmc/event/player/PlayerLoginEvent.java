package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerLoginEvent extends PlayerEvent implements Cancellable {

    private String kickReason = "Disconnected";
    private boolean canJoinFullServer = false;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerLoginEvent( Player player ) {
        super( player );
    }

    public String getKickReason() {
        return this.kickReason;
    }

    public void setKickReason( String kickReason ) {
        this.kickReason = kickReason;
    }

    public boolean canJoinFullServer() {
        return this.canJoinFullServer;
    }

    public void setCanJoinFullServer( boolean canJoinFullServer ) {
        this.canJoinFullServer = canJoinFullServer;
    }
}
