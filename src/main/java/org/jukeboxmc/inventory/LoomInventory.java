package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull InventoryType getType() {
        return InventoryType.LOOM;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.LOOM;
    }
}
