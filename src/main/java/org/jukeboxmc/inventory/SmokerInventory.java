package org.jukeboxmc.inventory;

import org.jukeboxmc.blockentity.BlockEntitySmoker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmokerInventory extends ContainerInventory {

    public SmokerInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public BlockEntitySmoker getInventoryHolder() {
        return (BlockEntitySmoker) this.holder;
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
