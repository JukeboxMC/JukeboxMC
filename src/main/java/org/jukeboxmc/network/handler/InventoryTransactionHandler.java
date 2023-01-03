package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockSlab;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.event.block.BlockPlaceEvent;
import org.jukeboxmc.event.player.PlayerDropItemEvent;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemAir;
import org.jukeboxmc.item.behavior.ItemBow;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InventoryTransactionHandler implements PacketHandler<InventoryTransactionPacket> {

    private long spamCheckTime;

    @Override
    public void handle( InventoryTransactionPacket packet, Server server, Player player ) {
        if ( packet.getTransactionType() == TransactionType.ITEM_USE ) {
            Vector blockPosition = new Vector( packet.getBlockPosition() );
            blockPosition.setDimension( player.getDimension() );
            Vector clickPosition = new Vector( packet.getClickPosition() );
            clickPosition.setDimension( player.getDimension() );
            BlockFace blockFace = BlockFace.fromId( packet.getBlockFace() );
            final Item itemInHand = player.getInventory().getItemInHand();
            switch ( packet.getActionType() ) {
                case 0 -> {
                    if ( !this.canInteract() ) {
                        player.getWorld().getBlock( player.getWorld().getSidePosition( blockPosition, blockFace ) ).sendUpdate( player );
                        return;
                    }
                    this.spamCheckTime = System.currentTimeMillis();
                    Vector placePosition = player.getWorld().getSidePosition( blockPosition, blockFace );
                    placePosition.setDimension( player.getDimension() );

                    player.setAction( false );

                    if ( !this.useItemOn( player, blockPosition, placePosition, clickPosition, blockFace ) ) {
                        Block blockClicked = player.getWorld().getBlock( blockPosition );
                        blockClicked.sendUpdate( player );

                        Block replacedBlock = player.getWorld().getBlock( blockPosition ).getSide( blockFace );
                        replacedBlock.sendUpdate( player );
                    }
                }
                case 1 -> {
                    Vector directionVector = player.getLocation().getDirection();
                    PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player, PlayerInteractEvent.Action.RIGHT_CLICK_AIR, itemInHand, directionVector );
                    Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

                    if ( itemInHand.useInAir( player, directionVector ) ) {
                        if ( !player.hasAction() ) {
                            player.setAction( true );
                            break;
                        }
                        player.setAction( false );

                        if ( !itemInHand.onUse( player ) ) {
                            player.getInventory().sendContents( player );
                        }
                    }
                }
                case 2 -> {
                    Vector breakPosition = new Vector( packet.getBlockPosition() );
                    breakPosition.setDimension( player.getDimension() );

                    Block block = player.getWorld().getBlock( breakPosition );
                    block.breakBlock( player, itemInHand );
                }
            }
        } else if ( packet.getTransactionType().equals( TransactionType.NORMAL ) ){
            for ( InventoryActionData action : packet.getActions() ) {
                if ( action.getSource().getType().equals( InventorySource.Type.WORLD_INTERACTION ) ) {
                    if ( action.getSource().getFlag().equals( InventorySource.Flag.DROP_ITEM ) ) {
                        Item targetItem = Item.create( action.getToItem() );
                        PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent( player, targetItem );
                        Server.getInstance().getPluginManager().callEvent( playerDropItemEvent );
                        if ( playerDropItemEvent.isCancelled() ) {
                            player.getInventory().sendContents( player );
                            return;
                        }
                        EntityItem entityItem = player.getWorld().dropItem(
                                playerDropItemEvent.getItem(),
                                player.getLocation().add( 0, player.getEyeHeight(), 0 ),
                                player.getLocation().getDirection().multiply( 0.4f, 0.4f, 0.4f ) );
                        entityItem.setPlayerHasThrown( player );
                        entityItem.spawn();
                    }
                } else if ( action.getSource().getType().equals( InventorySource.Type.CONTAINER ) ) {
                    int containerId = action.getSource().getContainerId();
                    if ( containerId == 0 ) {
                        Item check = player.getInventory().getItem( action.getSlot() );
                        Item sourceItem = new Item( action.getFromItem(), false );
                        Item targetItem = new Item( action.getToItem(), false );
                        if ( check.equalsExact( sourceItem ) ) {
                            player.getInventory().setItem( action.getSlot(), targetItem, false );
                        } else {
                            player.getInventory().sendContents( action.getSlot(), player );
                        }
                    }
                }
            }
        } else if ( packet.getTransactionType().equals( TransactionType.ITEM_USE_ON_ENTITY ) ) {
            switch ( packet.getActionType() ) {
                case 0 -> {
                    Entity interactEntity = player.getWorld().getEntity( packet.getRuntimeEntityId() );
                    if ( interactEntity != null ) {
                        interactEntity.interact( player, new Vector( packet.getClickPosition() ) );
                    }
                }
                case 1 -> {
                    Entity entity = player.getWorld().getEntity( packet.getRuntimeEntityId() );
                    if ( entity != null ) {
                        if ( player.attackWithItemInHand( entity ) ) {
                            if ( !player.getGameMode().equals( GameMode.CREATIVE ) ) {
                                Item itemInHand = player.getInventory().getItemInHand();
                                itemInHand.updateItem( player, 1 );
                            }
                        }
                    }
                }
                default -> {
                }
            }
        } else if ( packet.getTransactionType().equals( TransactionType.ITEM_RELEASE ) ) {
            if ( packet.getActionType() == 0 ) {
                if ( player.getInventory().getItemInHand() instanceof ItemBow ) {
                    ( (ItemBow) player.getInventory().getItemInHand() ).shoot( player );
                }
            }
            player.setAction( false );
        } else {
            player.setAction( false );
        }
    }

    public boolean useItemOn( Player player, Vector blockPosition, Vector placePosition, Vector clickedPosition, BlockFace blockFace ) {
        World world = player.getWorld();
        Block clickedBlock = world.getBlock( blockPosition );

        if ( clickedBlock.getType().equals( BlockType.AIR ) ) {
            return false;
        }

        Item itemInHand = player.getInventory().getItemInHand();

        Block replacedBlock = world.getBlock( placePosition );
        Block placedBlock = itemInHand.toBlock();

        Location location = new Location( world, placePosition, player.getDimension() );
        placedBlock.setLocation( location );

        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player,
                clickedBlock.getType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.RIGHT_CLICK_AIR :
                        PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, player.getInventory().getItemInHand(), clickedBlock );

        Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

        boolean interact = false;
        if ( !player.isSneaking() ) {
            if ( !playerInteractEvent.isCancelled() ) {
                interact = clickedBlock.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            }
        }

        boolean itemInteract = itemInHand.interact( player, blockFace, clickedPosition, clickedBlock );


        if ( !interact && itemInHand.useOnBlock( player, clickedBlock, location ) ) {
            return true;
        }

        if ( itemInHand instanceof ItemAir || itemInHand.toBlock().getType().equals( BlockType.AIR ) ) {
            return interact;
        }

        if ( ( !interact && !itemInteract ) || player.isSneaking() ) {
            if ( clickedBlock.canBeReplaced( placedBlock ) ) {
                placePosition = blockPosition;
            } else if ( !( replacedBlock.canBeReplaced( placedBlock ) || ( player.getInventory().getItemInHand().toBlock() instanceof BlockSlab && replacedBlock instanceof BlockSlab ) ) ) {
                return false;
            }

            if ( placedBlock.isSolid() ) {
                AxisAlignedBB boundingBox = player.getBoundingBox();
                if ( placedBlock.getBoundingBox().intersectsWith( boundingBox ) ) {
                    return false;
                }
            }

            if ( player.getGameMode().equals( GameMode.SPECTATOR ) ) {
                return false;
            }

            BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( player, placedBlock, replacedBlock, clickedBlock );
            Server.getInstance().getPluginManager().callEvent( blockPlaceEvent );

            if ( blockPlaceEvent.isCancelled() ) {
                return false;
            }
            boolean success = blockPlaceEvent.getPlacedBlock().placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
            if ( success ) {
                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    Item resultItem = itemInHand.setAmount( itemInHand.getAmount() - 1 );
                    if ( itemInHand.getAmount() != 0 ) {
                        player.getInventory().setItemInHand( resultItem );
                    } else {
                        player.getInventory().setItemInHand( Item.create( ItemType.AIR ) );
                    }
                    player.getInventory().sendContents( player.getInventory().getItemInHandSlot(), player );
                }
                world.playSound( placePosition, SoundEvent.PLACE, placedBlock.getRuntimeId() );
            }
            return success;
        }
        return true;
    }

    public boolean canInteract() {
        return !( System.currentTimeMillis() - this.spamCheckTime < 100 );
    }
}
