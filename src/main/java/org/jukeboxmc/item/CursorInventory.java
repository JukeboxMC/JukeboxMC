package org.jukeboxmc.item;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CursorInventory extends Inventory {

    public CursorInventory( Entity holder ) {
        super( holder, 1 );
    }

    @Override
    public void sendContents( Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        inventoryContentPacket.setWindowId( WindowId.CURSOR_DEPRECATED );
        inventoryContentPacket.setItems( this.contents );
        player.getPlayerConnection().sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents( int slot, Player player, boolean sendContents ) {
        if ( sendContents ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setWindowId( WindowId.CURSOR_DEPRECATED );
            inventorySlotPacket.setItem( this.contents[slot] );
            inventorySlotPacket.setSlot( slot );
            player.getPlayerConnection().sendPacket( inventorySlotPacket );
        }
    }
}
