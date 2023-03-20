package org.jukeboxmc.blockentity;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.HopperInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityHopper extends BlockEntity implements InventoryHolder {

    private final @NotNull HopperInventory hopperInventory;

    public BlockEntityHopper(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.hopperInventory = new HopperInventory( this );
    }

    @Override
    public boolean interact(@NotNull Player player, @NotNull Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.hopperInventory, blockPosition );
        return true;
    }

    public @NotNull HopperInventory getHopperInventory() {
        return this.hopperInventory;
    }
}
