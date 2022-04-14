package org.jukeboxmc.inventory;

import org.jukeboxmc.blockentity.BlockEntityChest;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.BlockEventPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class  DoubleChestInventory extends ChestInventory implements InventoryHolder {

    private final ChestInventory left;
    private final ChestInventory right;

    public DoubleChestInventory( InventoryHolder holder, ChestInventory left, ChestInventory right ) {
        super( holder, -1, left.getSize() + right.getSize());
        this.left = left;
        this.right = right;
    }

    @Override
    public void clear() {
        this.right.clear();
        this.left.clear();
    }

    @Override
    public BlockEntityChest getInventoryHolder() {
        return (BlockEntityChest) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.DOUBLE_CHEST;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.CONTAINER;
    }

    @Override
    public void setItem( int slot, Item item, boolean sendContent ) {
        if ( slot < this.left.getSize() ) {
            this.left.setItem( slot, item );
            if ( sendContent ) {
                for ( Player player : this.viewer ) {
                    this.sendContents( slot, player );
                }
            }
            return;
        }
        this.right.setItem( slot - this.left.getSize(), item );

        if ( sendContent ) {
            for ( Player player : this.viewer ) {
                this.sendContents( slot, player );
            }
        }
    }

    @Override
    public Item getItem( int slot ) {
        return slot < this.left.getSize() ? this.left.getItem( slot ) : this.right.getItem( slot - this.left.getSize() );
    }

    @Override
    public int getSize() {
       return this.left.getSize() + this.right.getSize();
    }

    @Override
    public Item[] getContents() {
        Item[] contents = new Item[this.left.getSize() + this.right.getSize()];
        System.arraycopy(this.left.getContents(), 0, contents, 0, this.left.getSize());
        System.arraycopy(this.right.getContents(), 0, contents, this.left.getSize(), this.right.getSize());
        return contents;
    }

    @Override
    public void onOpen( Player player ) {
        if ( this.viewer.size() == 1 ) {
            Location leftLocation = this.left.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setPosition( leftLocation );
            blockEventPacket.setData1( 1 );
            blockEventPacket.setData2( 2 );
            player.getWorld().sendChunkPacket( leftLocation.getBlockX() >> 4, leftLocation.getBlockZ() >> 4, blockEventPacket );

            Location rightLocation = this.right.getInventoryHolder().getBlock().getLocation();
            BlockEventPacket blockEventPacket2 = new BlockEventPacket();
            blockEventPacket2.setPosition( rightLocation );
            blockEventPacket2.setData1( 1 );
            blockEventPacket2.setData2( 2 );

            player.getWorld().playSound( rightLocation, LevelSound.CHEST_OPEN );
            player.getWorld().sendChunkPacket( rightLocation.getBlockX() >> 4, rightLocation.getBlockZ() >> 4, blockEventPacket2 );
        }
    }

    @Override
    public void onClose( Player player ) {
        if ( this.viewer.size() == 1 ) {
            Location leftLocation = this.left.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setPosition( leftLocation );
            blockEventPacket.setData1( 1 );
            blockEventPacket.setData2( 0 );
            player.getWorld().sendChunkPacket( leftLocation.getBlockX() >> 4, leftLocation.getBlockZ() >> 4, blockEventPacket );

            Location rightLocation = this.right.getInventoryHolder().getBlock().getLocation();
            BlockEventPacket blockEventPacket2 = new BlockEventPacket();
            blockEventPacket2.setPosition( rightLocation );
            blockEventPacket2.setData1( 1 );
            blockEventPacket2.setData2( 0 );

            player.getWorld().playSound( rightLocation, LevelSound.CHEST_CLOSED );
            player.getWorld().sendChunkPacket( rightLocation.getBlockX() >> 4, rightLocation.getBlockZ() >> 4, blockEventPacket2 );
        }
    }
}
