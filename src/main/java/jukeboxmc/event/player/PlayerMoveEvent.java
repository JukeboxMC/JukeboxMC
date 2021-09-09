package jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveEvent extends PlayerEvent implements Cancellable {

    private Location from;
    private Location to;

    /**
     * Creates a new {@link PlayerMoveEvent}
     *
     * @param player who moved
     * @param from   which represents the position the player stood at
     * @param to     which represents the players currently position
     */
    public PlayerMoveEvent( Player player, Location from, Location to ) {
        super( player );

        this.from = from;
        this.to = to;
    }

    /**
     * Retrieves the from {@link Location}
     *
     * @return a fresh {@link Location}
     */
    public Location getFrom() {
        return this.from;
    }

    /**
     * Modifies the from {@link Location}
     *
     * @param from which should be modified
     */
    public void setFrom( Location from ) {
        this.from = from;
    }

    /**
     * Retrieves the to {@link Location}
     *
     * @return a fresh {@link Location}
     */
    public Location getTo() {
        return this.to;
    }

    /**
     * Modifies the to {@link Location}
     *
     * @param to which should be modifies
     */
    public void setTo( Location to ) {
        this.to = to;
    }
}