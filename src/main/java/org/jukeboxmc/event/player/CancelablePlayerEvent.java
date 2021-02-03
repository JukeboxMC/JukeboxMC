package org.jukeboxmc.event.player;

import org.jukeboxmc.event.CancelableEvent;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CancelablePlayerEvent extends CancelableEvent {

    private Player player;

    public CancelablePlayerEvent( Player player ) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
