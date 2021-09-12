package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.BarrelInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBarrel extends BlockEntityContainer implements InventoryHolder {

    private final BarrelInventory barrelInventory;

    public BlockEntityBarrel( Block block ) {
        super( block );
        this.barrelInventory = new BarrelInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.barrelInventory, blockPosition );
        return true;
    }

    public BarrelInventory getBarrelInventory() {
        return this.barrelInventory;
    }
}
