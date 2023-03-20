package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.nbt.NbtType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.ChestInventory;
import org.jukeboxmc.inventory.ContainerInventory;
import org.jukeboxmc.inventory.DoubleChestInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityChest extends BlockEntity implements InventoryHolder {

    private final @NotNull ChestInventory chestInventory;
    private @Nullable DoubleChestInventory doubleChestInventory;
    private @Nullable Vector pairPosition;
    private boolean findable;
    private boolean setPaired;

    public BlockEntityChest(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.chestInventory = new ChestInventory( this );
    }

    @Override
    public void update( long currentTick ) {
        if ( this.isPaired() && !this.setPaired && this.isSpawned) {
            BlockEntityChest paired = this.getPaired();
            if ( paired == null) {
                return;
            }
            if ( paired.isSpawned ) {
                this.pair( paired );
            }
        }
    }

    @Override
    public boolean interact(@NotNull Player player, @NotNull Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        ContainerInventory chestInventory = this.getChestInventory();
        player.openInventory( chestInventory, blockPosition );
        return true;
    }

    @Override
    public void fromCompound(@NotNull NbtMap compound ) {
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
        this.pairPosition = new Vector( compound.getInt( "pairx", 0 ), this.getBlock().getLocation().getBlockY(), compound.getInt( "pairz", 0 ) );
        this.findable = compound.getBoolean( "Findable", false );
    }

    @Override
    public @NotNull NbtMapBuilder toCompound() {
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
        if ( this.pairPosition != null ) {
            builder.putInt( "pairx", this.pairPosition.getBlockX() );
            builder.putInt( "pairz", this.pairPosition.getBlockZ() );
        }
        builder.putBoolean( "Findable", this.findable );
        return builder;
    }

    public boolean isPaired() {
        if ( this.findable ) {
            return this.pairPosition != null;
        }
        return false;
    }

    public void pair(@NotNull BlockEntityChest other ) {
        Vector otherBP = other.getBlock().getLocation();
        long otherL = Utils.toLong( otherBP.getBlockX(), otherBP.getBlockZ() );

        Vector thisBP = this.getBlock().getLocation();
        long thisL = Utils.toLong( thisBP.getBlockX(), thisBP.getBlockZ() );

        if ( otherL > thisL ) {
            this.doubleChestInventory = new DoubleChestInventory( this, other.getChestInventory(), this.chestInventory );
        } else {
            this.doubleChestInventory = new DoubleChestInventory( this, this.chestInventory, other.getChestInventory() );
        }
        other.doubleChestInventory = this.doubleChestInventory;

        other.setPair( thisBP );
        this.setPair( otherBP );
    }

    public void unpair() {
        BlockEntityChest other = this.getPaired();
        if ( other != null ) {
            other.doubleChestInventory = null;
            other.resetPair();
        }

        this.doubleChestInventory = null;
        this.resetPair();
    }

    private void resetPair() {
        this.findable = false;
        this.pairPosition = null;
    }

    private @Nullable BlockEntityChest getPaired() {
        if ( this.pairPosition != null && this.isPaired() ) {
            Chunk loadedChunk = this.getBlock().getWorld().getLoadedChunk( this.pairPosition.getChunkX(), this.pairPosition.getChunkZ(), this.dimension );
            if ( loadedChunk != null ) {
                BlockEntity blockEntity = loadedChunk.getBlockEntity( this.pairPosition.getBlockX(), this.pairPosition.getBlockY(), this.pairPosition.getBlockZ() );
                if ( blockEntity instanceof BlockEntityChest blockEntityChest ) {
                    return blockEntityChest;
                }
            }
        }
        return null;
    }

    private void setPair(@NotNull Vector otherBP ) {
        this.findable = true;
        this.setPaired = true;
        this.pairPosition = new Vector( otherBP.getBlockX(), this.getBlock().getLocation().getBlockY(), otherBP.getBlockZ() );
    }

    public ChestInventory getRealInventory() {
        return this.chestInventory;
    }

    public ChestInventory getChestInventory() {
        return this.doubleChestInventory != null ? this.doubleChestInventory : this.chestInventory;
    }

    public void setDoubleChestInventory(@Nullable DoubleChestInventory doubleChestInventory ) {
        this.doubleChestInventory = doubleChestInventory;
    }
}
