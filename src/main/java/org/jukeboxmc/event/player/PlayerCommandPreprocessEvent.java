package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerCommandPreprocessEvent extends PlayerEvent implements Cancellable {

    private String command;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerCommandPreprocessEvent( Player player, String command ) {
        super( player );
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}
