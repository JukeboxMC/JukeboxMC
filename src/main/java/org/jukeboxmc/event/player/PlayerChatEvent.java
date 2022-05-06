package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerChatEvent extends PlayerEvent implements Cancellable {

    private String format;
    private String message;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerChatEvent( Player player,String format, String message ) {
        super( player );
        this.format = format;
        this.message = message;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat( String format ) {
        this.format = format;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
