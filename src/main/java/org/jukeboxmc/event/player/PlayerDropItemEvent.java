package org.jukeboxmc.event.player;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerDropItemEvent extends PlayerEvent implements Cancellable {

    private Item item;

    /**
     * Creates a new {@link PlayerEvent}
     *
     * @param player who represents the player which comes with this event
     */

    public PlayerDropItemEvent( Player player, Item item ) {
        super( player );
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem( Item item ) {
        this.item = item;
    }
}
