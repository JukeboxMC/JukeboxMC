package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CraftingTableInventory extends ContainerInventory {

    public CraftingTableInventory( InventoryHolder holder ) {
        super( holder, 10 );
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
