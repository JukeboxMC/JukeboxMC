package org.jukeboxmc.blockentity;

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
public class BlockEntityHopper extends BlockEntityContainer implements InventoryHolder {

    private final HopperInventory hopperInventory;

    public BlockEntityHopper( Block block ) {
        super( block );
        this.hopperInventory = new HopperInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.hopperInventory, blockPosition );
        return true;
    }

    public HopperInventory getHopperInventory() {
        return this.hopperInventory;
    }
}
