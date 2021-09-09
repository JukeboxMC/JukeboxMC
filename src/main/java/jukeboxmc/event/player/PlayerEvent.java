package jukeboxmc.event.player;

import org.jukeboxmc.event.Event;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class PlayerEvent extends Event {

    private Player player;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerEvent( Player player ) {
        this.player = player;
    }

    /**
     * Retrives the {@link Player} which comes with this {@link PlayerEvent}
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }
}