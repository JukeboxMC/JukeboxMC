package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmithingTableInventory extends ContainerInventory {

    public SmithingTableInventory( InventoryHolder holder) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.SMITHING_TABLE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.SMITHING_TABLE;
    }
}
