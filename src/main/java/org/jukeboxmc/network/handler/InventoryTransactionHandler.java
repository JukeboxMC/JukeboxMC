package org.jukeboxmc.network.handler;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.InventoryTransactionPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryTransactionHandler implements PacketHandler {

    private long spamCheckTime;

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

                            Inventory inventory = player.getInventory( WindowId.getWindowIdById( transaction.getWindowId() ) );
                            if ( inventory != null ) {
                                inventory.setItem( slot, targetItem );
                            } else {
                                System.out.println( "Inventory with id " + transaction.getWindowId() + " is missing" );
                            }
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
                BlockPosition blockPosition = transactionPacket.getBlockPosition();
                Vector clickPosition = transactionPacket.getClickPosition();
                BlockFace blockFace = transactionPacket.getBlockFace();

                switch ( transactionPacket.getActionType() ) {
                    case 0: //Place
                        if ( !this.canInteract() ) {
                            return;
                        }
                        this.spamCheckTime = System.currentTimeMillis();
                        player.setAction( false );
                        BlockPosition placePosition = player.getWorld().getSidePosition( blockPosition, blockFace );
                        if ( !player.getWorld().useItemOn( player, blockPosition, placePosition, clickPosition, blockFace ) ) {
                            Block blockClicked = player.getWorld().getBlock( blockPosition );
                            blockClicked.sendBlockUpdate( player.getPlayerConnection() );

                            Block replacedBlock = player.getWorld().getBlock( blockPosition ).getSide( blockFace );
                            replacedBlock.sendBlockUpdate( player.getPlayerConnection() );
                            return;
                        }
                        break;
                    case 1: //Click Air
                        System.out.println( "AIR" );
                        break;
                    case 2://Break
                        if ( player.getGameMode() == GameMode.CREATIVE ) {
                            player.getWorld().breakBlock( blockPosition, true );
                            return;
                        }
                        break;
                    default:
                        break;
                }
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

    public boolean canInteract() {
        return !( System.currentTimeMillis() - this.spamCheckTime < 100 );
    }
}
