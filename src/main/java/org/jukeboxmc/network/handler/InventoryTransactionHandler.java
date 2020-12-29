package org.jukeboxmc.network.handler;

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
                            player.getInventory().setItem( transaction.getSlot(), transaction.getNewItem(), false );
                            break;
                        case 2:
                            //DropItem
                            break;
                        default:
                            break;
                    }
                }
                break;
            case InventoryTransactionPacket.TYPE_USE_ITEM:
                System.out.println( "USE" );
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
}
