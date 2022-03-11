package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.BlastFurnaceInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBlastFurnace extends SmeltingComponent implements InventoryHolder {

    private final BlastFurnaceInventory blastFurnaceInventory;

    public BlockEntityBlastFurnace( Block block ) {
        super( block );
        this.blastFurnaceInventory = new BlastFurnaceInventory( this );
        this.initInventory( this.blastFurnaceInventory );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.blastFurnaceInventory, blockPosition );
        return super.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
    }

    public BlastFurnaceInventory getBlastFurnaceInventory() {
        return this.blastFurnaceInventory;
    }
}
