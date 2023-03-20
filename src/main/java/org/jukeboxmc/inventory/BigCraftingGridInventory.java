package org.jukeboxmc.inventory;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BigCraftingGridInventory extends CraftingGridInventory {

    public BigCraftingGridInventory( InventoryHolder holder ) {
        super( holder, -1, 9 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.BIG_CRAFTING_GRID;
    }

    @Override
    public void setItem(int slot, @NotNull Item item, boolean sendContent ) {
        super.setItem( slot - this.getOffset(), item, sendContent );
    }

    @Override
    public Item getItem( int slot ) {
        return super.getItem( slot - this.getOffset() );
    }

    @Override
    public int getOffset() {
        return 32;
    }
}
