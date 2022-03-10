package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.ShulkerBoxInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityShulkerBox extends BlockEntityContainer implements InventoryHolder {

    private byte facing = 1;
    private boolean undyed = false;

    private final ShulkerBoxInventory shulkerBoxInventory;

    public BlockEntityShulkerBox( Block block ) {
        super( block );
        this.shulkerBoxInventory = new ShulkerBoxInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.shulkerBoxInventory, blockPosition );
        return true;
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();
        builder.putList( "Items", NbtType.COMPOUND, new ArrayList<>() );
        builder.putByte( "facing", this.facing );
        builder.putBoolean( "isUndyed", this.undyed );
        return builder;
    }


    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );
        this.facing = compound.getByte( "facing" );
        this.undyed = compound.getBoolean( "isUndyed" );
    }

    public byte getFacing() {
        return this.facing;
    }

    public BlockEntityShulkerBox setFacing( byte facing ) {
        this.facing = facing;
        return this;
    }

    public boolean isUndyed() {
        return this.undyed;
    }

    public BlockEntityShulkerBox setUndyed( boolean undyed ) {
        this.undyed = undyed;
        return this;
    }

    public ShulkerBoxInventory getShulkerBoxInventory() {
        return this.shulkerBoxInventory;
    }
}
