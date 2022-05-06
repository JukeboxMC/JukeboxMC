package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import org.jukeboxmc.blockentity.BlockEntityChest;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ChestInventory extends ContainerInventory {

    public ChestInventory( InventoryHolder holder ) {
        super( holder, -1,27 );
    }

    public ChestInventory( InventoryHolder holder, int holderId, int slots ) {
        super( holder, holderId, slots );
    }

    @Override
    public BlockEntityChest getInventoryHolder() {
        return (BlockEntityChest) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void onOpen( Player player ) {
        if ( this.viewer.size() == 1 ) {
            Location location = this.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( location.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 2 );
            player.getWorld().playSound( location, SoundEvent.CHEST_OPEN );
            player.getWorld().sendChunkPacket( location.getBlockX() >> 4, location.getBlockZ() >> 4, blockEventPacket );
        }
    }

    @Override
    public void onClose( Player player ) {
        if ( this.viewer.size() == 1 ) {
            Location location = this.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( location.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 0 );
            player.getWorld().playSound( location, SoundEvent.CHEST_CLOSED );
            player.getWorld().sendChunkPacket( location.getBlockX() >> 4, location.getBlockZ() >> 4, blockEventPacket );
        }
    }
}
