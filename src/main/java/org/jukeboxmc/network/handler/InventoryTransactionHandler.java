package org.jukeboxmc.network.handler;

import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.packet.InventoryTransactionPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryTransactionHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        InventoryTransactionPacket transactionPacket = (InventoryTransactionPacket) packet;

        switch ( transactionPacket.getType() ) {
            case InventoryTransactionPacket.TYPE_NORMAL:
                for ( InventoryTransactionPacket.Transaction transaction : transactionPacket.getTransactions() ) {
                    switch ( transaction.getSourceType() ) {
                        case 0:
                            Item sourceItem = transaction.getOldItem();
                            Item targetItem = transaction.getNewItem();
                            int slot = transaction.getSlot();

                            Inventory inventory = this.getInventory( player, transaction.getWindowId() );
                            if ( inventory != null ) {
                                inventory.setItem( slot, targetItem, false );
                            }
                            break;
                        case 2:
                            //DropItem
                            break;
                        default:
                            break;
                    }
                }
                System.out.println( "Normal" );
                break;
            case InventoryTransactionPacket.TYPE_USE_ITEM:
                 int type = transactionPacket.getType();
                 System.out.println( "TYPE: " + type );
                 break;
            case InventoryTransactionPacket.TYPE_RELEASE_ITEM:
                System.out.println( "RELEASE" );
                break;
            case InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY:
                System.out.println( "USE_ON_ENTITY" );
                break;
            default:
                break;
        }
    }

    public Inventory getInventory( Player player, int windowId ) {
        switch ( windowId ) {
            case 0:
                return player.getInventory();
            case 124:
                return player.getCursorInventory();
            default:
                return null;
        }
    }
}
