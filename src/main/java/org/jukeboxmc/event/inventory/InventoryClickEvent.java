package org.jukeboxmc.event.inventory;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryClickEvent extends InventoryEvent implements Cancellable {

    private final Player player;

    private final Item sourceItem;
    private final Item targetItem;

    private final int slot;

    /**
     * Creates a new {@link InventoryClickEvent}
     *
     * @param inventory  which represents the inventory the {@link Player} clicked at
     * @param player     who clicked on a item in the {@link Inventory}
     * @param sourceItem which stands for the old item
     * @param targetItem which is the new item
     * @param slot       the slot where the item was placed at
     */
    public InventoryClickEvent( Inventory inventory, Player player, Item sourceItem, Item targetItem, int slot ) {
        super( inventory );
        this.player = player;
        this.sourceItem = sourceItem;
        this.targetItem = targetItem;
        this.slot = slot;
    }

    /**
     * Retrieves the {@link Player} who caused this {@link InventoryClickEvent}
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Retrieves the old {@link Item}
     *
     * @return a fresh {@link Item}
     */
    public Item getSourceItem() {
        return this.sourceItem;
    }

    /**
     * Retrieves the new {@link Item}
     *
     * @return a fresh {@link Item}
     */
    public Item getTargetItem() {
        return this.targetItem;
    }

    /**
     * Retrieves the slot
     *
     * @return a fresh int value
     */
    public int getSlot() {
        return slot;
    }

}