package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.inventory.InventoryClickEvent;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
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
                        case 0: // Slot Change Action
                            Item sourceItem = transaction.getOldItem();
                            Item targetItem = transaction.getNewItem();
                            int slot = transaction.getSlot();

                            WindowId windowIdById = WindowId.getWindowIdById( transaction.getWindowId() );
                            if ( windowIdById != null ) {
                                Inventory inventory = player.getInventory( windowIdById );
                                if ( inventory != null ) {
                                    InventoryClickEvent inventoryClickEvent = new InventoryClickEvent( inventory, player, sourceItem, targetItem, slot );
                                    Server.getInstance().getPluginManager().callEvent( inventoryClickEvent );

                                    if ( !inventoryClickEvent.isCancelled() ) {
                                        inventory.setItem( slot, targetItem );
                                    } else {
                                        player.getInventory().sendContents( player );
                                    }
                                } else {
                                    Server.getInstance().getLogger().debug( "Inventory with id " + transaction.getWindowId() + " is missing" );
                                }
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
                Vector blockPosition = transactionPacket.getBlockPosition();
                blockPosition.setDimension( player.getDimension() );

                Vector clickPosition = transactionPacket.getClickPosition();
                clickPosition.setDimension( player.getDimension() );

                BlockFace blockFace = transactionPacket.getBlockFace();
                Block block = player.getWorld().getBlock( blockPosition );

                switch ( transactionPacket.getActionType() ) {
                    case 0: //Use click block
                        if ( !this.canInteract() ) {
                            player.getWorld().getBlock( player.getWorld().getSidePosition( blockPosition, blockFace ) ).sendBlockUpdate( player );
                            return;
                        }
                        this.spamCheckTime = System.currentTimeMillis();
                        player.setAction( false );
                        Vector placePosition = player.getWorld().getSidePosition( blockPosition, blockFace );
                        placePosition.setDimension( player.getDimension() );

                        if ( !player.getWorld().useItemOn( player, blockPosition, placePosition, clickPosition, blockFace ) ) {
                            Block blockClicked = player.getWorld().getBlock( blockPosition );
                            blockClicked.sendBlockUpdate( player );

                            Block replacedBlock = player.getWorld().getBlock( blockPosition ).getSide( blockFace );
                            replacedBlock.sendBlockUpdate( player );
                            return;
                        }
                        break;
                    case 1: //Click Air
                        Vector directionVector = player.getLocation().getDirectionVector();
                        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                                player,
                                PlayerInteractEvent.Action.RIGHT_CLICK_AIR,
                                player.getInventory().getItemInHand(),
                                directionVector );
                        Server.getInstance().getPluginManager().callEvent( playerInteractEvent );
                        player.getInventory().getItemInHand().useInAir( player, directionVector );
                        break;
                    case 2://Break
                        if ( player.getGameMode() == GameMode.CREATIVE ) {
                            player.getWorld().breakBlock( player, blockPosition, player.getInventory().getItemInHand() );
                            return;
                        }
                        break;
                    default:
                        break;
                }
                break;
            case InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY:
                Server.getInstance().getLogger().debug( "USE_ON_ENTITY" );
                break;
            case InventoryTransactionPacket.TYPE_RELEASE_ITEM:
                Server.getInstance().getLogger().debug( "RELEASE" );
                break;
            default:
                break;
        }
    }

    public boolean canInteract() {
        return !( System.currentTimeMillis() - this.spamCheckTime < 100 );
    }
}
