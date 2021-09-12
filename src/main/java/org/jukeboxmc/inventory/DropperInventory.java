package org.jukeboxmc.inventory;

import org.jukeboxmc.blockentity.BlockEntityDropper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DropperInventory extends ContainerInventory {

    public DropperInventory( InventoryHolder holder ) {
        super( holder, -1, 9 );
    }

    @Override
    public BlockEntityDropper getInventoryHolder() {
        return (BlockEntityDropper) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.DROPPER;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.DROPPER;
    }
}