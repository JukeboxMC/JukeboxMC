package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancelable;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveEvent extends PlayerEvent implements Cancelable {

    private Location from;
    private Location to;

    public PlayerMoveEvent(Player player, Location from, Location to) {
        super(player);

        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return this.from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return this.to;
    }

    public void setTo(Location to) {
        this.to = to;
    }
}