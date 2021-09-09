package jukeboxmc.event.inventory;

import org.jukeboxmc.event.Event;
import org.jukeboxmc.inventory.Inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class InventoryEvent extends Event {

    private Inventory inventory;

    /**
     * Creates a new {@link InventoryEvent}
     *
     * @param inventory the representation of the event's affected {@link Inventory}
     */
    public InventoryEvent( Inventory inventory ) {
        this.inventory = inventory;
    }

    /**
     * Retrieves the affected {@link Inventory} which comes with this {@link InventoryEvent}
     *
     * @return a fresh {@link Inventory}
     */
    public Inventory getInventory() {
        return this.inventory;
    }
}