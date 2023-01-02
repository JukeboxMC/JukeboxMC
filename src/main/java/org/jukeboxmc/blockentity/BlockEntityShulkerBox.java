package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.ShulkerBoxInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityShulkerBox extends BlockEntity implements InventoryHolder {

    private byte facing = 1;
    private boolean undyed = false;

    private final ShulkerBoxInventory shulkerBoxInventory;

    public BlockEntityShulkerBox( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.shulkerBoxInventory = new ShulkerBoxInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.shulkerBoxInventory, blockPosition );
        return true;
    }

    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );

        List<NbtMap> items = compound.getList( "Items", NbtType.COMPOUND );
        for ( NbtMap nbtMap : items ) {
            Item item = this.toItem( nbtMap );
            byte slot = nbtMap.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                this.shulkerBoxInventory.addItem( item, false );
            } else {
                this.shulkerBoxInventory.setItem( slot, item, false );
            }
        }
        this.facing = compound.getByte( "facing" );
        this.undyed = compound.getBoolean( "isUndyed" );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();
        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < this.shulkerBoxInventory.getSize(); slot++ ) {
            NbtMapBuilder itemCompound = NbtMap.builder();
            Item item = this.shulkerBoxInventory.getItem( slot );

            itemCompound.putByte( "Slot", (byte) slot );
            this.fromItem( item, itemCompound );

            itemsCompoundList.add( itemCompound.build() );
        }
        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );
        builder.putByte( "facing", this.facing );
        builder.putBoolean( "isUndyed", this.undyed );
        return builder;
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
