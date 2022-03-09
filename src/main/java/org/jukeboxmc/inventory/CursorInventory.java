package org.jukeboxmc.inventory;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CursorInventory extends Inventory {

    public CursorInventory( InventoryHolder holder, long holderId ) {
        super( holder, holderId, 51 );
    }

    public void setItem( Item item ) {
        this.setItem( 0, item );
    }

    public Item getItem() {
        return this.getItem( 0 );
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
        player.sendPacket( inventorySlotPacket );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CURSOR;
    }
}
