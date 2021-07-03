package org.jukeboxmc.event.inventory;

import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryCloseEvent extends InventoryEvent {

    private Player player;

    public InventoryCloseEvent( Inventory inventory, Player player ) {
        super( inventory );
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
