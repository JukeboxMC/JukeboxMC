package org.jukeboxmc.inventory;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentTableInventory extends ContainerInventory {

    public EnchantmentTableInventory( InventoryHolder holder ) {
        super( holder, 2 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ENCHANTMENT_TABLE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.ENCHANTMENT;
    }
}
