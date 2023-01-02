package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.BarrelInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBarrel extends BlockEntity implements InventoryHolder {

    private final BarrelInventory barrelInventory;

    public BlockEntityBarrel( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.barrelInventory = new BarrelInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.barrelInventory, blockPosition );
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
                this.barrelInventory.addItem( item, false );
            } else {
                this.barrelInventory.setItem( slot, item, false );
            }
        }
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();

        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < this.barrelInventory.getSize(); slot++ ) {
            NbtMapBuilder itemCompound = NbtMap.builder();
            Item item = this.barrelInventory.getItem( slot );

            itemCompound.putByte( "Slot", (byte) slot );
            this.fromItem( item, itemCompound );

            itemsCompoundList.add( itemCompound.build() );
        }
        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );
        return builder;
    }

    public BarrelInventory getBarrelInventory() {
        return this.barrelInventory;
    }
}
