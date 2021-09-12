package org.jukeboxmc.inventory;

import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnvilInventory extends ContainerInventory {

    public AnvilInventory( InventoryHolder holder, long holderId ) {
        super( holder, holderId, 3 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ANVIL;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.ANVIL;
    }
}
