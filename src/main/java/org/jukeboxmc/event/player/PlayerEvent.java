package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Event;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerEvent extends Event {

    private Player player;

    public PlayerEvent( Player player ) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
