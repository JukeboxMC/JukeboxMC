package org.jukeboxmc.blockentity;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.nbt.NbtType;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.FurnaceInventory;
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
public class BlockEntityFurnace extends SmeltingComponent implements InventoryHolder {

    private final FurnaceInventory furnaceInventory;

    public BlockEntityFurnace( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.furnaceInventory = new FurnaceInventory( this );
        this.initInventory( this.furnaceInventory );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.furnaceInventory, blockPosition );
        return super.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
    }

    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );
        List<NbtMap> items = compound.getList( "Items", NbtType.COMPOUND );
        for ( NbtMap nbtMap : items ) {
            Item item = this.toItem( nbtMap );
            byte slot = nbtMap.getByte( "Slot", (byte) 127 );
            if ( slot == 127 ) {
                this.furnaceInventory.addItem( item, false );
            } else {
                this.furnaceInventory.setItem( slot, item, false );
            }
        }
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();

        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < this.furnaceInventory.getSize(); slot++ ) {
            NbtMapBuilder itemCompound = NbtMap.builder();
            Item item = this.furnaceInventory.getItem( slot );
            itemCompound.putByte( "Slot", (byte) slot );
            this.fromItem( item, itemCompound );
            itemsCompoundList.add( itemCompound.build() );
        }

        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );

        return builder;
    }

    public FurnaceInventory getFurnaceInventory() {
        return this.furnaceInventory;
    }

}
