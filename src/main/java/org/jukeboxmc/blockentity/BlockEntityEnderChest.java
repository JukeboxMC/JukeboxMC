package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.EnderChestInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityEnderChest extends BlockEntityContainer implements InventoryHolder {

    public BlockEntityEnderChest( Block block ) {
        super( block );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();
        enderChestInventory.setPosition( blockPosition );
        player.openInventory( enderChestInventory, blockPosition );
        return true;
    }
}
