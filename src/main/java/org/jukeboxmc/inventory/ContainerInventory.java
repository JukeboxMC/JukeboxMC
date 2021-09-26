package org.jukeboxmc.inventory;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.ContainerOpenPacket;
import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ContainerInventory extends Inventory {

    public ContainerInventory( InventoryHolder holder, long holderId, int slots ) {
        super( holder, holderId, slots );
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.CONTAINER;
    }

    @Override
    public void removeViewer( Player player ) {
        this.onClose( player );

        super.removeViewer( player );
    }

    @Override
    public void sendContents( Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        inventoryContentPacket.setWindowId( WindowId.OPEN_CONTAINER );
        inventoryContentPacket.setItems( this.getContents() );
        player.sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents( int slot, Player player ) {
        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        inventorySlotPacket.setWindowId( WindowId.OPEN_CONTAINER );
        inventorySlotPacket.setSlot( slot );
        inventorySlotPacket.setItem( this.contents[slot] );
        player.sendPacket( inventorySlotPacket );
    }

    public void addViewer( Player player, Vector position, byte windowId ) {
        ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
        containerOpenPacket.setEntityId( this.holderId );
        containerOpenPacket.setWindowId( windowId );
        containerOpenPacket.setWindowTypeId( this.getWindowTypeId() );
        containerOpenPacket.setPosition( position );
        player.sendPacket( containerOpenPacket );

        super.addViewer( player );

        this.onOpen( player );
    }

    public void onOpen( Player player ) {

    }

    public void onClose( Player player ) {

    }

}
