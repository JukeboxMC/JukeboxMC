package org.jukeboxmc.inventory;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GrindstoneInventory extends ContainerInventory {

    public GrindstoneInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.GRINDSTONE;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.GRINDSTONE;
    }
}
