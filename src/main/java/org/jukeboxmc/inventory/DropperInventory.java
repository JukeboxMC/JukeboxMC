package org.jukeboxmc.inventory;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.blockentity.BlockEntityDropper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DropperInventory extends ContainerInventory {

    public DropperInventory( InventoryHolder holder ) {
        super( holder, -1, 9 );
    }

    @Override
    public BlockEntityDropper getInventoryHolder() {
        return (BlockEntityDropper) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.DROPPER;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.DROPPER;
    }
}
