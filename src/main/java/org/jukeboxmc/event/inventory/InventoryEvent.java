package org.jukeboxmc.event.inventory;

import org.jukeboxmc.event.Event;
import org.jukeboxmc.inventory.Inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryEvent extends Event {

    private Inventory inventory;

    public InventoryEvent( Inventory inventory ) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
