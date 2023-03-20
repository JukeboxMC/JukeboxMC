package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.blockentity.BlockEntityChest;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DoubleChestInventory extends ChestInventory implements InventoryHolder {

    private final @NotNull ChestInventory left;
    private final @NotNull ChestInventory right;

    public DoubleChestInventory(InventoryHolder holder, @NotNull ChestInventory left, @NotNull ChestInventory right ) {
        super( holder, left.getSize() + right.getSize());
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
    public @NotNull InventoryType getType() {
        return InventoryType.DOUBLE_CHEST;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void setItem(int slot, @NotNull Item item, boolean sendContent ) {
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
    public Item @NotNull [] getContents() {
        Item[] contents = new Item[this.left.getSize() + this.right.getSize()];
        System.arraycopy(this.left.getContents(), 0, contents, 0, this.left.getSize());
        System.arraycopy(this.right.getContents(), 0, contents, this.left.getSize(), this.right.getSize());
        return contents;
    }

    @Override
    public void onOpen(@NotNull Player player ) {
        this.sendContents( player );
        if ( this.viewer.size() == 1 ) {
            Location leftLocation = this.left.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( leftLocation.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 2 );
            player.getWorld().sendChunkPacket( leftLocation.getBlockX() >> 4, leftLocation.getBlockZ() >> 4, blockEventPacket );

            Location rightLocation = this.right.getInventoryHolder().getBlock().getLocation();
            BlockEventPacket blockEventPacket2 = new BlockEventPacket();
            blockEventPacket2.setBlockPosition( rightLocation.toVector3i() );
            blockEventPacket2.setEventType( 1 );
            blockEventPacket2.setEventData( 2 );

            player.getWorld().playSound( rightLocation, SoundEvent.CHEST_OPEN );
            player.getWorld().sendChunkPacket( rightLocation.getBlockX() >> 4, rightLocation.getBlockZ() >> 4, blockEventPacket2 );
        }
    }

    @Override
    public void onClose(@NotNull Player player ) {
        if ( this.viewer.size() == 0 ) {
            Location leftLocation = this.left.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( leftLocation.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 0 );
            player.getWorld().sendChunkPacket( leftLocation.getBlockX() >> 4, leftLocation.getBlockZ() >> 4, blockEventPacket );

            Location rightLocation = this.right.getInventoryHolder().getBlock().getLocation();
            BlockEventPacket blockEventPacket2 = new BlockEventPacket();
            blockEventPacket2.setBlockPosition( rightLocation.toVector3i() );
            blockEventPacket2.setEventType( 1 );
            blockEventPacket2.setEventData( 0 );

            player.getWorld().playSound( rightLocation, SoundEvent.CHEST_CLOSED );
            player.getWorld().sendChunkPacket( rightLocation.getBlockX() >> 4, rightLocation.getBlockZ() >> 4, blockEventPacket2 );
        }
    }
}
