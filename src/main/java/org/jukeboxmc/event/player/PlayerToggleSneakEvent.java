package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerToggleSneakEvent extends PlayerEvent implements Cancellable {

    private boolean isSneaking;

    public PlayerToggleSneakEvent( Player player, boolean isSneaking ) {
        super( player );

        this.isSneaking = isSneaking;
    }

    public boolean isSneaking() {
        return this.isSneaking;
    }
}