package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.inventory.transaction.InventoryAction;
import org.jukeboxmc.inventory.transaction.InventoryTransaction;
import org.jukeboxmc.inventory.transaction.action.*;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemBow;
import org.jukeboxmc.math.Vector;
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

        for ( InventoryActionData transaction : packet.getActions() ) {
            InventoryAction inventoryAction = this.createInventory( player, transaction );
            if ( inventoryAction != null ) {
                actions.add( inventoryAction );
            } else {
                break;
            }
        }

        if ( this.isCraftingPart( packet ) ) {
            if ( player.getCraftingTransaction() == null ) {
                player.createCraftingTransaction( actions );
            } else {
                for ( InventoryAction action : actions ) {
                    player.getCraftingTransaction().addAction( action );
                }
            }

            if ( player.getCraftingTransaction().getPrimaryOutput() != null && player.getCraftingTransaction().canExecute() ) {
                player.getCraftingTransaction().execute();
                player.resetCraftingTransaction();
            }
            return;
        } else if ( player.getCraftingTransaction() != null ) {
            if ( player.getCraftingTransaction().checkForCraftingPart( actions ) ) {
                for ( InventoryAction action : actions ) {
                    player.getCraftingTransaction().addAction( action );
                }
            }
            return;
        }


        if ( packet.getTransactionType() == TransactionType.NORMAL ) {
            InventoryTransaction transaction = new InventoryTransaction( player, actions );
            transaction.execute();
        }

        switch ( packet.getTransactionType() ) {
            case ITEM_USE -> {
                Vector blockPosition = new Vector( packet.getBlockPosition() );
                blockPosition.setDimension( player.getDimension() );
                Vector clickPosition = new Vector( packet.getClickPosition() );
                clickPosition.setDimension( player.getDimension() );
                BlockFace blockFace = BlockFace.fromId( packet.getBlockFace() );
                switch ( packet.getActionType() ) {
                    case 0 -> { //Use item on
                        if ( !this.canInteract() ) {
                            player.getWorld().getBlock( player.getWorld().getSidePosition( blockPosition, blockFace ) ).sendBlockUpdate( player );
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
                        }
                    }
                    case 1 -> { //Click in air
                        Vector directionVector = player.getLocation().getDirection();
                        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player, PlayerInteractEvent.Action.RIGHT_CLICK_AIR, player.getInventory().getItemInHand(), directionVector );
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

                    }
                    case 2 -> { //Block break
                        player.getWorld().breakBlock( player, new Vector( packet.getBlockPosition() ), player.getInventory().getItemInHand() );
                    }
                }
            }
            case ITEM_USE_ON_ENTITY -> {
                switch ( packet.getActionType() ) {
                    case 0:
                        //Interact Entity
                        break;
                    case 1:
                        Entity entity = player.getWorld().getEntity( packet.getRuntimeEntityId() );
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
            case ITEM_RELEASE -> {
                if ( packet.getActionType() == 0 ) {
                    if ( player.getInventory().getItemInHand() instanceof ItemBow ) {
                        ( (ItemBow) player.getInventory().getItemInHand() ).shoot( player );
                    }
                }
                player.setAction( false );
            }
            default -> player.setAction( false );
        }
    }

    private InventoryAction createInventory( Player player, InventoryActionData inventoryActionData ) {
        InventorySource source = inventoryActionData.getSource();
        final Item sourceItem = Item.fromItemData( inventoryActionData.getFromItem() );
        final Item targetItem = Item.fromItemData( inventoryActionData.getToItem() );
        switch ( source.getType() ) {
            case CONTAINER -> {
                Inventory inventory = player.getInventory( WindowId.getWindowIdById( inventoryActionData.getSource().getContainerId() ), inventoryActionData.getSlot() );
                if ( inventory != null ) {
                    return new SlotChangeAction( inventory, inventoryActionData.getSlot(), sourceItem, targetItem );
                }
                return null;
            }
            case WORLD_INTERACTION -> {
                return new DropItemAction( sourceItem, targetItem );
            }
            case CREATIVE -> {
                return new CreativeInventoryAction( sourceItem, targetItem );
            }
            case UNTRACKED_INTERACTION_UI, NON_IMPLEMENTED_TODO -> {
                switch ( inventoryActionData.getSource().getContainerId() ) {
                    case -4:
                        return new CraftingTakeResultAction( sourceItem, targetItem );
                    case -5:
                        return new CraftingTransferMaterialAction( sourceItem, targetItem );
                    default:
                        break;
                }
            }
        }
        return null;
    }

    public boolean isCraftingPart( InventoryTransactionPacket packet ) {
        for ( InventoryActionData inventoryActionData : packet.getActions() ) {
            InventorySource source = inventoryActionData.getSource();
            if ( source.getType() == InventorySource.Type.NON_IMPLEMENTED_TODO &&
                    source.getContainerId() == -4 ||
                    source.getContainerId() == -5 ) {
                return true;
            }
        }
        return false;
    }

    public boolean canInteract() {
        return !( System.currentTimeMillis() - this.spamCheckTime < 100 );
    }
}
