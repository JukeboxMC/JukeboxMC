package org.jukeboxmc.inventory;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CreativeItemCacheInventory extends Inventory{

    public CreativeItemCacheInventory( InventoryHolder holder ) {
        super( holder, 1 );
    }

    @Override
    public void sendContents( Player player ) {

    }

    @Override
    public void sendContents( int slot, Player player ) {

    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.CREATIVE;
    }
}
