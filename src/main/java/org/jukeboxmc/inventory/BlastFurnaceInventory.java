package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.blockentity.BlockEntityBlastFurnace;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlastFurnaceInventory extends ContainerInventory {

    public BlastFurnaceInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public BlockEntityBlastFurnace getInventoryHolder() {
        return (BlockEntityBlastFurnace) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.BLAST_FURNACE;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.BLAST_FURNACE;
    }
}
