package org.jukeboxmc.event.player;

import org.jukeboxmc.event.CancelablePlayerEvent;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSwimEvent extends CancelablePlayerEvent {

    private boolean isSwimming;

    public PlayerToggleSwimEvent( Player player, boolean isSwimming ) {
        super( player );
        this.isSwimming = isSwimming;
    }

    public boolean isSwimming() {
        return this.isSwimming;
    }
}
