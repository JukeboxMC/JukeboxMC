package org.jukeboxmc.event.inventory;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

    private Player player;

    public InventoryOpenEvent( Inventory inventory, Player player ) {
        super( inventory );
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

}
