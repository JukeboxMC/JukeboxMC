package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlastFurnaceInventory extends ContainerInventory{

    public BlastFurnaceInventory( InventoryHolder holder) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.BLAST_FURNACE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.BLAST_FURNACE;
    }
}
