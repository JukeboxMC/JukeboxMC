package org.jukeboxmc.inventory;

import org.jukeboxmc.blockentity.BlockEntityLoom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoomInventory extends ContainerInventory {

    public LoomInventory( InventoryHolder holder ) {
        super( holder, -1, 4 );
    }

    @Override
    public BlockEntityLoom getInventoryHolder() {
        return (BlockEntityLoom) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.LOOM;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.LOOM;
    }
}
