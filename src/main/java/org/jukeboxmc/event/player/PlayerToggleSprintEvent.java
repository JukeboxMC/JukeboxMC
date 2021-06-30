package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable {

    private boolean isSprinting;

    /**
     * Creates a new {@link PlayerToggleSprintEvent}
     *
     * @param player      who toggled sprinting
     * @param isSprinting whether the player is sprinting
     */
    public PlayerToggleSprintEvent( Player player, boolean isSprinting ) {
        super( player );
        this.isSprinting = isSprinting;
    }

    /**
     * Retrieves whether the player is sprinting or not
     *
     * @return whether the player is sprinting
     */
    public boolean isSprinting() {
        return this.isSprinting;
    }
}