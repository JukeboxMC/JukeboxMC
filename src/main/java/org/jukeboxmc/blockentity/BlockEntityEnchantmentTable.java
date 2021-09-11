package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.EnchantmentTableInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityEnchantmentTable extends BlockEntityContainer implements InventoryHolder {

    private final EnchantmentTableInventory enchantmentTableInventory;

    public BlockEntityEnchantmentTable( Block block ) {
        super( block );
        this.enchantmentTableInventory = new EnchantmentTableInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.enchantmentTableInventory, blockPosition );
        return true;
    }

    public EnchantmentTableInventory getEnchantmentTableInventory() {
        return this.enchantmentTableInventory;
    }
}
