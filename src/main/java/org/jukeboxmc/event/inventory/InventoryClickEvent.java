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

    private Player player;

    private Item sourceItem;
    private Item targetItem;

    private int slot;

    public InventoryClickEvent( Inventory inventory, Player player, Item sourceItem, Item targetItem, int slot ) {
        super( inventory );
        this.player = player;
        this.sourceItem = sourceItem;
        this.targetItem = targetItem;
        this.slot = slot;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Item getSourceItem() {
        return this.sourceItem;
    }

    public void setSourceItem( Item sourceItem ) {
        this.sourceItem = sourceItem;
    }

    public Item getTargetItem() {
        return this.targetItem;
    }

    public void setTargetItem( Item targetItem ) {
        this.targetItem = targetItem;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot( int slot ) {
        this.slot = slot;
    }
}
