package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FurnaceInventory extends ContainerInventory {

    public FurnaceInventory( InventoryHolder holder ) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.FURNACE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.FURNACE;
    }
}
