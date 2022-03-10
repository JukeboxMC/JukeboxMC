package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.FurnaceInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityFurnace extends SmeltingComponent implements InventoryHolder {

    private final FurnaceInventory furnaceInventory;

    public BlockEntityFurnace( Block block ) {
        super( block );
        this.furnaceInventory = new FurnaceInventory( this );
        this.initInventory( this.furnaceInventory );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.furnaceInventory, blockPosition );
        return super.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
    }

    public FurnaceInventory getFurnaceInventory() {
        return this.furnaceInventory;
    }
}
