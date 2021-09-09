package jukeboxmc.event.inventory;

import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryCloseEvent extends InventoryEvent {

    private Player player;

    /**
     * Creates a new {@link InventoryCloseEvent}
     *
     * @param inventory which was closed
     * @param player    who closed the {@link Inventory}
     */
    public InventoryCloseEvent( Inventory inventory, Player player ) {
        super( inventory );
        this.player = player;
    }

    /**
     * Retrieves the {@link Player} who caused this {@link InventoryCloseEvent}
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }
}