package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable {

    private boolean isSprinting;

    public PlayerToggleSprintEvent( Player player, boolean isSprinting ) {
        super( player );
        this.isSprinting = isSprinting;
    }

    public boolean isSprinting() {
        return this.isSprinting;
    }
}