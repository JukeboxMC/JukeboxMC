package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSwimEvent extends PlayerEvent implements Cancellable {

    private boolean isSwimming;

    /**
     * Creates a new {@link PlayerToggleSwimEvent}
     *
     * @param player     who toggled swimming
     * @param isSwimming whether the player is swimming
     */
    public PlayerToggleSwimEvent( Player player, boolean isSwimming ) {
        super( player );
        this.isSwimming = isSwimming;
    }

    /**
     * Retrieves whether the player is swimming or not
     *
     * @return whether the player is swimming
     */
    public boolean isSwimming() {
        return this.isSwimming;
    }
}