package org.jukeboxmc.inventory;

import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;
import org.cloudburstmc.protocol.bedrock.packet.BlockEventPacket;
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
    public InventoryType getType() {
        return InventoryType.ENDER_CHEST;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void onOpen( Player player ) {
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
    public void onClose( Player player ) {
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
