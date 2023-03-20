package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.blockentity.BlockEntityFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FurnaceInventory extends ContainerInventory {

    public FurnaceInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public BlockEntityFurnace getInventoryHolder() {
        return (BlockEntityFurnace) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.FURNACE;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.FURNACE;
    }
}
