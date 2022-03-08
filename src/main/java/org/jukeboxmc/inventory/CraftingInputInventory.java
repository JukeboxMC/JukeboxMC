package org.jukeboxmc.inventory;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CraftingInputInventory extends CraftingInventory {

    public CraftingInputInventory( InventoryHolder holder ) {
        super( holder, -1, 41 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CRAFTING;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.WORKBENCH;
    }

    @Override
    public void onClose( Player player ) {
        for ( Item content : player.getCraftingInputInventory().getContents() ) {
            player.getWorld().dropItem( content, player.getLocation(), null );
        }

        player.getCraftingInputInventory().clear();
    }
}
