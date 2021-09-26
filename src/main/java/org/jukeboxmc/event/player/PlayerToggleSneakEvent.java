package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSneakEvent extends PlayerEvent implements Cancellable {

    private final boolean isSneaking;

    /**
     * Creates a new {@link PlayerToggleSneakEvent}
     *
     * @param player     who toggled sneaking
     * @param isSneaking whether the player is sneaking
     */
    public PlayerToggleSneakEvent( Player player, boolean isSneaking ) {
        super( player );

        this.isSneaking = isSneaking;
    }

    /**
     * Retrieves whether the player is sneaking or not
     *
     * @return whether the player is sneaking
     */
    public boolean isSneaking() {
        return this.isSneaking;
    }
}