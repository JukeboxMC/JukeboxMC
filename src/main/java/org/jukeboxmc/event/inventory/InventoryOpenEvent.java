package org.jukeboxmc.event.inventory;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

    private final Player player;

    /**
     * Creates a new {@link InventoryOpenEvent}
     *
     * @param inventory which was opened
     * @param player    who opened the {@link Inventory}
     */
    public InventoryOpenEvent( Inventory inventory, Player player ) {
        super( inventory );
        this.player = player;
    }

    /**
     * Retrieves the {@link Player} who caused this {@link InventoryOpenEvent}
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }
}