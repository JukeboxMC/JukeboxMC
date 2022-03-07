package org.jukeboxmc.inventory;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CraftingTableInventory extends ContainerInventory {

    public CraftingTableInventory( InventoryHolder holder ) {
        super( holder, -1, 9 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.WORKBENCH;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.WORKBENCH;
    }


}
