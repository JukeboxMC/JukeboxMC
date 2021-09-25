package org.jukeboxmc.event.player;

import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerRespawnEvent extends PlayerEvent {

    private Location respawnLocation;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */
    public PlayerRespawnEvent( Player player, Location respawnLocation ) {
        super( player );
        this.respawnLocation = respawnLocation;
    }

    public Location getRespawnLocation() {
        return this.respawnLocation;
    }

    public void setRespawnLocation( Location respawnLocation ) {
        this.respawnLocation = respawnLocation;
    }
}
