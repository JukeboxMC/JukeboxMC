package org.jukeboxmc.event.player;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerLoginEvent extends PlayerEvent{

    private String kickReason = "Disconnected";
    private Result result = Result.ALLOWED;

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

    public void setResult( Result result ) {
        this.result = result;
    }

    public Result getResult() {
        return this.result;
    }

    public static enum Result {
        ALLOWED,
        SERVER_FULL,
        WHITELIST,
        XBOX_AUTHENTICATED,
        OTHER
    }
}
