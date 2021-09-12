package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.ChestInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityChest extends BlockEntityContainer implements InventoryHolder {

    private final ChestInventory chestInventory;

    public BlockEntityChest( Block block ) {
        super( block );
        this.chestInventory = new ChestInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.chestInventory, blockPosition, (byte) player.generateWindowId() );
        return true;
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();
        builder.putList( "Items", NbtType.COMPOUND, new ArrayList<>() );
        builder.putInt( "pairX", this.getBlock().getLocation().getBlockX() );
        builder.putInt( "pairZ", this.getBlock().getLocation().getBlockY() );
        builder.putBoolean( "Findable", true );
        return builder;
    }
}
