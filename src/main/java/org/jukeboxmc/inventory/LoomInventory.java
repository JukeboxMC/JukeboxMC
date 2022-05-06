package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
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
    public InventoryType getInventoryType() {
        return InventoryType.LOOM;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.LOOM;
    }
}
