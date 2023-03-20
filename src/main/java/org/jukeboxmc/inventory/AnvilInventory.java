package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnvilInventory extends ContainerInventory {

    public AnvilInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.ANVIL;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.ANVIL;
    }

    @Override
    public void setItem(int slot, @NotNull Item item, boolean sendContent ) {
        super.setItem( slot - 1, item, sendContent );
    }

    @Override
    public Item getItem( int slot ) {
        return super.getItem( slot - 1 );
    }
}
