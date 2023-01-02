package org.jukeboxmc.blockentity;

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

    private final DropperInventory dropperInventory;

    public BlockEntityDropper( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.dropperInventory = new DropperInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.dropperInventory, blockPosition );
        return true;
    }

    public DropperInventory getDropperInventory() {
        return this.dropperInventory;
    }
}
