package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmokerInventory extends ContainerInventory {

    public SmokerInventory( InventoryHolder holder ) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.SMOKER;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.SMOKER;
    }
}
