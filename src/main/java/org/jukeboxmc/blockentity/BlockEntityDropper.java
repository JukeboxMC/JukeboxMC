package org.jukeboxmc.blockentity;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.DropperInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDropper extends BlockEntity implements InventoryHolder {

    private final @NotNull DropperInventory dropperInventory;

    public BlockEntityDropper(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.dropperInventory = new DropperInventory( this );
    }

    @Override
    public boolean interact(@NotNull Player player, @NotNull Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.dropperInventory, blockPosition );
        return true;
    }

    public @NotNull DropperInventory getDropperInventory() {
        return this.dropperInventory;
    }
}
