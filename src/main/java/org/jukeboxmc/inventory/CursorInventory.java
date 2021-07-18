package org.jukeboxmc.inventory;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CursorInventory extends Inventory {

    public CursorInventory( InventoryHolder holder ) {
        super( holder, 1 );
    }

    public void setItem( Item item ) {
       this.setItem( 0, item );
    }

    @Override
    public void sendContents( Player player ) {
        this.sendContents( 0, player );
    }

    @Override
    public void sendContents( int slot, Player player ) {
        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        inventorySlotPacket.setWindowId( WindowId.CURSOR_DEPRECATED );
        inventorySlotPacket.setItem( this.contents[slot] );
        inventorySlotPacket.setSlot( slot );
        player.getPlayerConnection().sendPacket( inventorySlotPacket );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CURSOR;
    }
}
