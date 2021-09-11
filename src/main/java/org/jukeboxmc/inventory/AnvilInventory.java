package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnvilInventory extends ContainerInventory {

    public AnvilInventory( InventoryHolder holder ) {
        super( holder, 3 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ANVIL;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.ANVIL;
    }
}
