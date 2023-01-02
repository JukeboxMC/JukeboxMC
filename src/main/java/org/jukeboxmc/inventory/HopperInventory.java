package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.blockentity.BlockEntityHopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HopperInventory extends ContainerInventory {

    public HopperInventory( InventoryHolder holder) {
        super( holder, -1, 5 );
    }

    @Override
    public BlockEntityHopper getInventoryHolder() {
        return (BlockEntityHopper) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.HOPPER;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.HOPPER;
    }
}
