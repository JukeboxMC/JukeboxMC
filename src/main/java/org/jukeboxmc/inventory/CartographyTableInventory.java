package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CartographyTableInventory extends ContainerInventory {

    public CartographyTableInventory( InventoryHolder holder) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CARTOGRAPHY_TABLE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.CARTOGRAPHY;
    }
}
