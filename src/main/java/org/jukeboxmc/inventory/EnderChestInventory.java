package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EnderChestInventory extends ContainerInventory {

    private Vector position;

    public EnderChestInventory( InventoryHolder holder ) {
        super( holder, -1, 27 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.ENDER_CHEST;
    }

    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void onOpen(@NotNull Player player ) {
        super.onOpen( player );
        if ( this.viewer.size() == 1 ) {
            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( this.position.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 2 );
            player.getWorld().playSound( this.position, SoundEvent.ENDERCHEST_OPEN );
            player.getWorld().sendChunkPacket( this.position.getBlockX() >> 4, this.position.getBlockZ() >> 4, blockEventPacket );
        }
    }

    @Override
    public void onClose(@NotNull Player player ) {
        if ( this.viewer.size() == 0 ) {
            BlockEventPacket blockEventPacket = new BlockEventPacket();
            blockEventPacket.setBlockPosition( this.position.toVector3i() );
            blockEventPacket.setEventType( 1 );
            blockEventPacket.setEventData( 0 );
            player.getWorld().playSound( this.position, SoundEvent.ENDERCHEST_CLOSED );
            player.getWorld().sendChunkPacket( this.position.getBlockX() >> 4, this.position.getBlockZ() >> 4, blockEventPacket );
        }
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition( Vector position ) {
        this.position = position;
    }
}
