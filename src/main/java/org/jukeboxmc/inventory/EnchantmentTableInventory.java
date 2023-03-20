package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull InventoryType getType() {
        return InventoryType.ENCHANTMENT_TABLE;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.ENCHANTMENT;
    }
}
