package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HopperInventory extends ContainerInventory {

    public HopperInventory( InventoryHolder holder) {
        super( holder, -1, 5 );
    }

    @Override
    public InventoryHolder getInventoryHolder() {
        return null;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.HOPPER;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.HOPPER;
    }
}
