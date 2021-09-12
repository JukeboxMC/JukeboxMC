package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HopperInventory extends ContainerInventory {

    public HopperInventory( InventoryHolder holder) {
        super( holder, -1, 5 );
    }

    @Override
    public InventoryHolder getInventoryHolder() {
        return null;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.HOPPER;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.HOPPER;
    }
}
