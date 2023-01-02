package org.jukeboxmc.inventory;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmallCraftingGridInventory extends CraftingGridInventory {

    public SmallCraftingGridInventory( InventoryHolder holder ) {
        super( holder, -1, 4 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.SMALL_CRAFTING_GRID;
    }

    @Override
    public void setItem( int slot, Item item, boolean sendContent ) {
        super.setItem( slot - this.getOffset(), item, sendContent );
    }

    @Override
    public Item getItem( int slot ) {
        return super.getItem( slot - this.getOffset() );
    }

    @Override
    public int getOffset() {
        return 28;
    }
}
