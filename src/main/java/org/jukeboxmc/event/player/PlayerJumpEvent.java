package org.jukeboxmc.event.player;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerJumpEvent extends PlayerEvent {

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerJumpEvent( Player player ) {
        super( player );
    }
}
