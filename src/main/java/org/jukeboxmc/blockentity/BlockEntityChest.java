package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.ChestInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityChest extends BlockEntityContainer implements InventoryHolder {

    private final ChestInventory chestInventory;
    private int pairX;
    private int pairZ;
    private boolean findable;

    public BlockEntityChest( Block block ) {
        super( block );
        this.chestInventory = new ChestInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        player.openInventory( this.chestInventory, blockPosition );
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
                this.chestInventory.addItem( item, false );
            } else {
                this.chestInventory.setItem( slot, item, false );
            }
        }

        this.pairX = compound.getInt( "pairx", 0 );
        this.pairZ = compound.getInt( "pairz", 0 );
        this.findable = compound.getBoolean( "Findable", false );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder builder = super.toCompound();

        List<NbtMap> itemsCompoundList = new ArrayList<>();
        for ( int slot = 0; slot < this.chestInventory.getSize(); slot++ ) {
            NbtMapBuilder itemCompound = NbtMap.builder();
            Item item = this.chestInventory.getItem( slot );

            itemCompound.putByte( "Slot", (byte) slot );
            this.fromItem( item, itemCompound );

            itemsCompoundList.add( itemCompound.build() );
        }

        builder.putList( "Items", NbtType.COMPOUND, itemsCompoundList );
        builder.putInt( "pairx", this.pairX );
        builder.putInt( "pairz", this.pairZ );
        builder.putBoolean( "Findable", this.findable );
        return builder;
    }

    public ChestInventory getChestInventory() {
        return this.chestInventory;
    }
}
