package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.inventory.transaction.InventoryTransaction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBow;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.InventoryTransactionPacket;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryTransactionHandler implements PacketHandler<InventoryTransactionPacket> {

    private long spamCheckTime;

    @Override
    public void handle( InventoryTransactionPacket packet, Server server, Player player ) {
        List<InventoryAction> actions = new ArrayList<>();

        for ( InventoryTransactionPacket.Transaction transaction : packet.getTransactions() ) {
            InventoryAction inventoryAction = transaction.createInventory( player );
            if ( inventoryAction != null){
                actions.add( inventoryAction );
            }
        }

        if ( packet.getType() == InventoryTransactionPacket.TYPE_NORMAL ) {
            InventoryTransaction transaction = new InventoryTransaction(player, actions);
            transaction.execute();
            return;
        }

        switch ( packet.getType() ) {
            case InventoryTransactionPacket.TYPE_USE_ITEM -> {
                Vector blockPosition = packet.getBlockPosition();
                blockPosition.setDimension( player.getDimension() );
                Vector clickPosition = packet.getClickPosition();
                clickPosition.setDimension( player.getDimension() );
                BlockFace blockFace = packet.getBlockFace();
                Block block = player.getWorld().getBlock( blockPosition );
                switch ( packet.getActionType() ) {
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
                        Vector directionVector = player.getLocation().getDirection();
                        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(
                                player,
                                PlayerInteractEvent.Action.RIGHT_CLICK_AIR,
                                player.getInventory().getItemInHand(),
                                directionVector );
                        Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

                        if ( player.getInventory().getItemInHand().useInAir( player, directionVector ) ) {
                            if ( !player.hasAction() ) {
                                player.setAction( true );
                                break;
                            }
                            player.setAction( false );

                            if ( !player.getInventory().getItemInHand().onUse( player ) ) {
                                player.getInventory().sendContents( player );
                            }
                        }

                        break;
                    case 2://Break
                        player.getWorld().breakBlock( player, blockPosition, player.getInventory().getItemInHand() );
                        break;
                    default:
                        break;
                }
            }
            case InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY -> {
                Server.getInstance().getLogger().debug( "USE_ON_ENTITY" );
                switch ( packet.getActionType() ) {
                    case 0: //Entity Interact
                        break;
                    case 1: //Entity Attack
                        Entity entity = player.getWorld().getEntity( packet.getEntityId() );
                        if ( entity != null ) {
                            if ( player.attackWithItemInHand( entity ) ) {
                                if ( !player.getGameMode().equals( GameMode.CREATIVE ) ) {
                                    Item itemInHand = player.getInventory().getItemInHand();
                                    itemInHand.updateItem( player, 1 );
                                }
                            }
                        }

                        break;
                    default:
                        break;
                }
            }
            case InventoryTransactionPacket.TYPE_RELEASE_ITEM -> {
                Server.getInstance().getLogger().debug( "RELEASE" );
                if ( packet.getActionType() == 0 ) { // Release item ( shoot bow )
                    // Check if the item is a bow
                    if ( player.getInventory().getItemInHand() instanceof ItemBow ) {
                        ( (ItemBow) player.getInventory().getItemInHand() ).shoot( player );
                    }
                }
                player.setAction( false );
            }
            default -> player.setAction( false );
        }
    }


    private Inventory getInventory( Player player, InventoryTransactionPacket.Transaction transaction ) {
        switch ( WindowId.getWindowIdById( transaction.getWindowId() ) ) {
            case PLAYER:
                return player.getInventory();
            case CURSOR_DEPRECATED:
                /*
                Inventory inventory;
                if ( transaction.getSlot() > 0 ) {
                    if ( transaction.getSlot() == 50 ) {
                        transaction.setSlot( 0 );
                        inventory = player.getCursorInventory();
                    } else {
                        if ( transaction.getSlot() >= 28 && transaction.getSlot() <= 31 ) {
                            inventory = player.getCraftingTableInventory();
                            inventory.setSlotSize( 4 );
                            transaction.setSlot( transaction.getSlot() - 28 );
                        } else if ( transaction.getSlot() >= 32 && transaction.getSlot() <= 40 ) {
                            inventory = player.getCraftingTableInventory();
                            inventory.setSlotSize( 9 );
                            transaction.setSlot( transaction.getSlot() - 32 );
                        } else {
                            inventory = player.getCursorInventory();
                        }
                    }
                } else {
                    inventory = player.getCursorInventory();
                }
                 */
                return player.getCursorInventory();
            case ARMOR_DEPRECATED:
                return player.getArmorInventory();
            default:
                return player.getCurrentInventory();
        }
    }


/*
    private Inventory getInventory( Player player, InventoryTransactionPacket.Transaction transaction ) {
        switch ( WindowId.getWindowIdById( transaction.getWindowId() ) ) {
            case PLAYER:
                return player.getInventory();
            case CURSOR_DEPRECATED:
                int slot = transaction.getSlot();
                if ( slot >= 28 && slot <= 31 ) {
                    //PlayerCrafting
                    return player.getCursorInventory();
                } else if ( slot >= 32 && slot <= 40 ) {
                    //CraftingTable
                    return player.getCraftingTableInventory();
                } else {
                    return player.getCursorInventory();
                }
            case ARMOR_DEPRECATED:
                return player.getArmorInventory();
            default:
                return player.getCurrentInventory();
        }
    }
 */

    public boolean canInteract() {
        return !( System.currentTimeMillis() - this.spamCheckTime < 100 );
    }
}
