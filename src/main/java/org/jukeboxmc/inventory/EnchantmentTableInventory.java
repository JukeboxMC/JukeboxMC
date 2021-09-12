package org.jukeboxmc.inventory;

import org.jukeboxmc.blockentity.BlockEntityEnchantmentTable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnchantmentTableInventory extends ContainerInventory {

    public EnchantmentTableInventory( InventoryHolder holder ) {
        super( holder, -1, 2 );
    }

    @Override
    public BlockEntityEnchantmentTable getInventoryHolder() {
        return (BlockEntityEnchantmentTable) this.holder;
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
