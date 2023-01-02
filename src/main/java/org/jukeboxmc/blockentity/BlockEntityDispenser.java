package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.DispenserInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDispenser extends BlockEntity implements InventoryHolder {

    private final DispenserInventory dispenserInventory;

    public BlockEntityDispenser( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.dispenserInventory = new DispenserInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.dispenserInventory, blockPosition );
        return true;
    }

    public DispenserInventory getDispenserInventory() {
        return this.dispenserInventory;
    }
}
