package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.FurnaceInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityFurnace extends BlockEntityContainer implements InventoryHolder {

    private FurnaceInventory furnaceInventory;

    public BlockEntityFurnace( Block block ) {
        super( block );
        this.furnaceInventory = new FurnaceInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.furnaceInventory, blockPosition );
        return true;
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );

    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.put( "id", "Furnace" );
        return compound;
    }

    public FurnaceInventory getFurnaceInventory() {
        return this.furnaceInventory;
    }
}
