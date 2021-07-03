package org.jukeboxmc.inventory;

import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ContainerInventory extends Inventory {

    public ContainerInventory( InventoryHolder holder, int slots ) {
        super( holder, slots );
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.CONTAINER;
    }

    @Override
    public void addViewer( Player player ) {
        super.addViewer( player );
    }

    @Override
    public void removeViewer( Player player ) {
        super.removeViewer( player );
    }

    @Override
    public void sendContents( Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        inventoryContentPacket.setWindowId( WindowId.OPEN_CONTAINER );
        inventoryContentPacket.setItems( this.getContents() );
        player.getPlayerConnection().sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents( int slot, Player player ) {
        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        inventorySlotPacket.setWindowId( WindowId.OPEN_CONTAINER );
        inventorySlotPacket.setSlot( slot );
        inventorySlotPacket.setItem( this.contents[slot] );
        player.getPlayerConnection().sendPacket( inventorySlotPacket );
    }
}
