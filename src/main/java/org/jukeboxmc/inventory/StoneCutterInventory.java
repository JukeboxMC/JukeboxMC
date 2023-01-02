package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class StoneCutterInventory extends ContainerInventory {

    public StoneCutterInventory( InventoryHolder holder ) {
        super( holder, -1, 2 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.STONE_CUTTER;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.STONECUTTER;
    }
}
