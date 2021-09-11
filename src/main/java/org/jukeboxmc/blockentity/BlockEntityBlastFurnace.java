package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.BlastFurnaceInventory;
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
public class BlockEntityBlastFurnace extends BlockEntityContainer implements InventoryHolder {

    private final BlastFurnaceInventory blastFurnaceInventory;

    public BlockEntityBlastFurnace( Block block ) {
        super( block );
        this.blastFurnaceInventory = new BlastFurnaceInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.blastFurnaceInventory, blockPosition );
        return true;
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.put( "id", "BlastFurnace" );
        return compound;
    }

    public BlastFurnaceInventory getBlastFurnaceInventory() {
        return this.blastFurnaceInventory;
    }
}
