package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.skin.Skin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerChangeSkinEvent extends PlayerEvent implements Cancellable {

    private Skin skin;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerChangeSkinEvent( Player player, Skin skin ) {
        super( player );
        this.skin = skin;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public void setSkin( Skin skin ) {
        this.skin = skin;
    }
}
