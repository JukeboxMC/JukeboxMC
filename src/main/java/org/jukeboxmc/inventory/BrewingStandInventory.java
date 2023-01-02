package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.blockentity.BlockEntityBrewingStand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BrewingStandInventory extends ContainerInventory {

    public BrewingStandInventory( InventoryHolder holder) {
        super( holder, -1, 5 );
    }

    @Override
    public BlockEntityBrewingStand getInventoryHolder() {
        return (BlockEntityBrewingStand) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.BREWING_STAND;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.BREWING_STAND;
    }
}
