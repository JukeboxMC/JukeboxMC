package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmithingTableInventory extends ContainerInventory {

    public SmithingTableInventory( InventoryHolder holder ) {
        super( holder, -1, 3 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.SMITHING_TABLE;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.SMITHING_TABLE;
    }
}
