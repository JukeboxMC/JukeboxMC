package org.jukeboxmc.inventory;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
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
    public InventoryType getType() {
        return InventoryType.FURNACE;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.FURNACE;
    }
}
