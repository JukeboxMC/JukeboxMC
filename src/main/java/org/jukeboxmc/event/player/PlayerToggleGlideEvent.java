package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleGlideEvent extends PlayerEvent implements Cancellable {

    private boolean isGliding;

    public PlayerToggleGlideEvent( Player player, boolean isGliding ) {
        super( player );

        this.isGliding = isGliding;
    }

    public boolean isGliding() {
        return this.isGliding;
    }
}