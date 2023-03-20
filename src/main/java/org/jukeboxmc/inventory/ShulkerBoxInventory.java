package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.blockentity.BlockEntityShulkerBox;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ShulkerBoxInventory extends ContainerInventory {

    public ShulkerBoxInventory( InventoryHolder holder ) {
        super( holder, -1, 27 );
    }

    @Override
    public BlockEntityShulkerBox getInventoryHolder() {
        return (BlockEntityShulkerBox) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.SHULKER_BOX;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void onOpen(@NotNull Player player ) {
        super.onOpen( player );
        if ( this.viewer.size() == 1 ) {
            Location location = this.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( location.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 2 );
            player.getWorld().playSound( location, SoundEvent.SHULKERBOX_OPEN );
            player.getWorld().sendChunkPacket( location.getBlockX() >> 4, location.getBlockZ() >> 4, blockEventPacket );
        }
    }

    @Override
    public void onClose(@NotNull Player player ) {
        if ( this.viewer.size() == 0 ) {
            Location location = this.getInventoryHolder().getBlock().getLocation();

            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( location.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 0 );
            player.getWorld().playSound( location, SoundEvent.SHULKERBOX_CLOSED );
            player.getWorld().sendChunkPacket( location.getBlockX() >> 4, location.getBlockZ() >> 4, blockEventPacket );
        }
    }
}
