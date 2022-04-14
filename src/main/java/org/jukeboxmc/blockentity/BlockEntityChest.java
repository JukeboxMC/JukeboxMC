package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockChest;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.ChestInventory;
import org.jukeboxmc.inventory.ContainerInventory;
import org.jukeboxmc.inventory.DoubleChestInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityChest extends BlockEntityContainer implements InventoryHolder {

    private final ChestInventory chestInventory;
    private DoubleChestInventory doubleChestInventory;
    private int pairX;
    private int pairZ;
    private boolean findable;

    public BlockEntityChest( Block block ) {
        super( block );
        this.chestInventory = new ChestInventory( this );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        ContainerInventory chestInventory = this.getChestInventory();
        player.openInventory( chestInventory, blockPosition );
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


        if ( this.isPaired() ) {
            BlockEntityChest paired = this.getPaired();
            if ( paired == null ) {
                return;
            }
            this.pair( paired );
        } else {
            this.unpair();
        }

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

    public boolean isPaired() {
        if ( this.findable ) {
            Vector position = this.getBlock().getLocation();
            Block other = this.getBlock().getWorld().getBlock( this.pairX, position.getBlockY(), this.pairZ );
            return other.getBlockType().equals( this.getBlock().getBlockType() );
        }
        return false;
    }

    public void pair( BlockEntityChest other ) {
        // Get the positions of both sides of the pair
        Vector otherBP = other.getBlock().getLocation();
        long otherL = Utils.toLong( otherBP.getBlockX(), otherBP.getBlockZ() );

        Vector thisBP = this.getBlock().getLocation();
        long thisL = Utils.toLong( thisBP.getBlockX(), thisBP.getBlockZ() );

        // Order them according to "natural" ordering in the world
        if ( otherL > thisL ) {
            this.doubleChestInventory = new DoubleChestInventory( this, other.getChestInventory(), this.chestInventory );
        } else {
            this.doubleChestInventory = new DoubleChestInventory( this, this.chestInventory, other.getChestInventory() );
        }
        other.doubleChestInventory = this.doubleChestInventory;

        // Set the other pair side into the tiles
        other.setPair( thisBP );
        this.setPair( otherBP );
    }

    public void unpair() {
        BlockEntityChest other = this.getPaired();
        if (other != null) {
            other.doubleChestInventory = null;
            other.resetPair();
        }

        this.doubleChestInventory = null;
        this.resetPair();
    }

    private void resetPair() {
        this.findable = false;
        this.pairX = 0;
        this.pairZ = 0;
    }

    private BlockEntityChest getPaired() {
        if (!this.isPaired()) {
            return null;
        }
        Vector position = this.getBlock().getLocation();
        BlockChest other = (BlockChest) this.getBlock().getWorld().getBlock(this.pairX, position.getBlockY(), this.pairZ);
        return other.getBlockEntity();
    }

    private void setPair(Vector otherBP) {
        this.findable = true;
        this.pairX = otherBP.getBlockX();
        this.pairZ = otherBP.getBlockZ();
    }

    public ChestInventory getRealInventory() {
        return this.chestInventory;
    }

    public ChestInventory getChestInventory() {
        return this.doubleChestInventory != null ? this.doubleChestInventory : this.chestInventory;
    }

    public void setDoubleChestInventory( DoubleChestInventory doubleChestInventory ) {
        this.doubleChestInventory = doubleChestInventory;
    }
}
