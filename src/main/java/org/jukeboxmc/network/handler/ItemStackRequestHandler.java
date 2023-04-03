package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.event.inventory.InventoryClickEvent;
import org.jukeboxmc.event.player.PlayerDropItemEvent;
import org.jukeboxmc.inventory.CraftingGridInventory;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.InventoryType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.enchantment.EnchantmentType;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.CreativeItems;

import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStackRequestHandler implements PacketHandler<ItemStackRequestPacket> {

    @Override
    public void handle( ItemStackRequestPacket packet, Server server, Player player ) {
        List<ItemStackResponse> responses = new LinkedList<>();
        for ( ItemStackRequest request : packet.getRequests() ) {
            Map<Integer, List<ItemStackResponseSlot>> itemEntryMap = new HashMap<>();
            for ( ItemStackRequestAction action : request.getActions() ) {
                switch ( action.getType() ) {
                    case CONSUME -> {
                        ItemStackResponseSlot itemEntry = this.handleConsumeAction( player, (ConsumeAction) action, request ).get( 0 );
                        if ( !itemEntryMap.containsKey( request.getRequestId() ) ) {
                            itemEntryMap.put( request.getRequestId(), new LinkedList<>() {{
                                add( itemEntry );
                            }} );
                        } else {
                            itemEntryMap.get( request.getRequestId() ).add( itemEntry );
                        }
                    }
                    case CRAFT_CREATIVE ->
                            this.handleCraftCreativeAction( player, (CraftCreativeAction) action );
                    case CRAFT_RECIPE ->
                            responses.addAll( this.handleCraftRecipeAction( player, (CraftRecipeAction) action, request ) );
                    case TAKE ->
                            responses.addAll( this.handleTakeStackRequestAction( player, (TakeAction) action, request ) );
                    case PLACE ->
                            responses.addAll( this.handlePlaceAction( player, (PlaceAction) action, request ) );
                    case DESTROY ->
                            responses.addAll( this.handleDestroyAction( player, (DestroyAction) action, request ) );
                    case SWAP ->
                            responses.addAll( this.handleSwapAction( player, (SwapAction) action, request ) );
                    case DROP ->
                            responses.addAll( this.handleDropItemAction( player, (DropAction) action, request ) );
                    case CRAFT_RECIPE_OPTIONAL ->
                            responses.addAll( this.handleCraftRecipeOptionalAction( player, (CraftRecipeOptionalAction) action, request ) );
                    case CRAFT_RESULTS_DEPRECATED -> {
                        //responses.addAll( this.handleCraftResult( player, (CraftResultsDeprecatedStackRequestActionData) action, requestId ) );
                    }
                    default ->
                            server.getLogger().info( "Unhandelt Action: " + action.getClass().getSimpleName() + " : " + action.getType() );
                }
            }
            ItemStackResponsePacket itemStackResponsePacket = new ItemStackResponsePacket();
            Map<Integer, List<ItemStackResponseContainer>> containerEntryMap = new HashMap<>();
            if ( !itemEntryMap.isEmpty() ) {
                for ( ItemStackResponse respons : responses ) {
                    containerEntryMap.put( respons.getRequestId(), respons.getContainers() );
                }
                if ( containerEntryMap.containsKey( request.getRequestId() ) ) {
                    containerEntryMap.get( request.getRequestId() ).add( 0, new ItemStackResponseContainer( ContainerSlotType.CRAFTING_INPUT, itemEntryMap.get( request.getRequestId() ) ) );
                }
                for ( Map.Entry<Integer, List<ItemStackResponseContainer>> entry : containerEntryMap.entrySet() ) {
                    itemStackResponsePacket.getEntries().add( new ItemStackResponse( ItemStackResponseStatus.OK, entry.getKey(), entry.getValue() ) );
                }
            } else {
                itemStackResponsePacket.getEntries().addAll( responses );
            }
            player.getPlayerConnection().sendPacket( itemStackResponsePacket );
        }
    }

    private Collection<ItemStackResponse> handleCraftRecipeOptionalAction( Player player, CraftRecipeOptionalAction action, ItemStackRequest request ) {
        return Collections.emptyList();
    }

    private Collection<ItemStackResponse> handleCraftResult( Player player, CraftResultsDeprecatedAction action, int requestId ) {
        CraftingGridInventory craftingGridInventory = player.getCraftingGridInventory();

        List<ItemStackResponseSlot> itemEntries = new LinkedList<>();
        for ( int slot = craftingGridInventory.getOffset(); slot < craftingGridInventory.getSize() + craftingGridInventory.getOffset(); slot++ ) {
            Item item = craftingGridInventory.getItem( slot );
            itemEntries.add( new ItemStackResponseSlot( (byte) slot, (byte) slot, (byte) item.getAmount(), item.getStackNetworkId(), item.getDisplayname(), item.getDurability() ) );
        }

        List<ItemStackResponseContainer> containerEntryList = new LinkedList<>();
        containerEntryList.add( new ItemStackResponseContainer( ContainerSlotType.CRAFTING_INPUT, itemEntries ) );

        return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, requestId, containerEntryList ) );
    }

    private List<ItemStackResponseSlot> handleConsumeAction( Player player, ConsumeAction action, ItemStackRequest request ) {
        int amount = action.getCount();
        ItemStackRequestSlotData source = action.getSource();

        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );

        sourceItem.setAmount( sourceItem.getAmount() - amount );
        if ( sourceItem.getAmount() <= 0 ) {
            sourceItem = Item.create( ItemType.AIR );
        }

        this.setItem( player, source.getContainer(), source.getSlot(), sourceItem );

        List<ItemStackResponseSlot> containerEntryList = new LinkedList<>();
        containerEntryList.add( new ItemStackResponseSlot(
                source.getSlot(),
                source.getSlot(),
                (byte) sourceItem.getAmount(),
                sourceItem.getStackNetworkId(),
                sourceItem.getDisplayname(),
                sourceItem.getDurability()
        ) );
        return containerEntryList;
    }

    private List<ItemStackResponse> handleCraftRecipeAction( Player player, CraftRecipeAction action, ItemStackRequest request ) {
        List<Item> resultItem = Server.getInstance().getCraftingManager().getResultItem( action.getRecipeNetworkId() );
        player.getCreativeItemCacheInventory().setItem( 0, resultItem.get( 0 ) );
        return Collections.emptyList();
    }

    private void handleCraftCreativeAction( Player player, CraftCreativeAction actionData ) {
        ItemData itemData = CreativeItems.getCreativeItems().get( actionData.getCreativeItemNetworkId() - 1 );
        Item item = Item.create( itemData );
        item.setAmount( item.getMaxStackSize() );
        player.getCreativeItemCacheInventory().setItem( 0, item );
    }

    private List<ItemStackResponse> handlePlaceAction( Player player, PlaceAction actionData, ItemStackRequest itemStackRequest ) {
        int amount = actionData.getCount();
        ItemStackRequestSlotData source = actionData.getSource();
        ItemStackRequestSlotData destination = actionData.getDestination();

        List<ItemStackResponseContainer> containerEntryList = new LinkedList<>();
        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );
        Item destinationItem = this.getItem( player, destination.getContainer(), destination.getSlot() );

        if ( source.getContainer().equals( ContainerSlotType.CREATED_OUTPUT ) ) {
            Inventory sourceInventory = this.getInventory( player, source.getContainer() );
            Inventory destinationInventory = this.getInventory( player, destination.getContainer() );

            InventoryClickEvent inventoryClickEvent = new InventoryClickEvent( sourceInventory, destinationInventory, player, sourceItem, destinationItem, source.getSlot() );
            Server.getInstance().getPluginManager().callEvent( inventoryClickEvent );

            if ( inventoryClickEvent.isCancelled() ) {
                sourceInventory.setItem( source.getSlot(), sourceItem );
                return List.of(
                        new ItemStackResponse( ItemStackResponseStatus.ERROR, itemStackRequest.getRequestId(), Collections.emptyList() ) );
            }

            sourceItem.setStackNetworkId( Item.stackNetworkCount++ );
            if ( !destinationItem.getType().equals( ItemType.AIR ) ) {
                sourceItem.setAmount( Math.min( destinationItem.getAmount() + sourceItem.getAmount(), sourceItem.getMaxStackSize() ) );
            }
            this.setItem( player, destination.getContainer(), destination.getSlot(), sourceItem );
            containerEntryList.add( new ItemStackResponseContainer(
                    destination.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    destination.getSlot(),
                                    destination.getSlot(),
                                    (byte) sourceItem.getAmount(),
                                    sourceItem.getStackNetworkId(),
                                    sourceItem.getDisplayname(),
                                    sourceItem.getDurability()
                            )
                    )
            ) );
            return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), containerEntryList ) );
        } else {
            Inventory sourceInventory = this.getInventory( player, source.getContainer() );
            Inventory destinationInventory = this.getInventory( player, destination.getContainer() );
            InventoryClickEvent inventoryClickEvent = new InventoryClickEvent( sourceInventory, destinationInventory, player, sourceItem, destinationItem, source.getSlot() );
            Server.getInstance().getPluginManager().callEvent( inventoryClickEvent );

            if ( inventoryClickEvent.isCancelled() || ( sourceInventory.getType().equals( InventoryType.ARMOR ) && sourceItem.getEnchantment( EnchantmentType.CURSE_OF_BINDING ) != null ) ) {
                sourceInventory.setItem( source.getSlot(), sourceItem );
                return List.of(
                        new ItemStackResponse( ItemStackResponseStatus.ERROR, itemStackRequest.getRequestId(), Collections.emptyList() ) );
            }

            if ( destinationItem.equals( sourceItem ) && sourceItem.getAmount() > 0 ) {
                sourceItem.setAmount( sourceItem.getAmount() - amount );
                if ( sourceItem.getAmount() <= 0 ) {
                    sourceItem = Item.create( ItemType.AIR );
                }
                destinationItem.setAmount( destinationItem.getAmount() + amount );
            } else if ( destinationItem.getType().equals( ItemType.AIR ) ) {
                if ( sourceItem.getAmount() == amount ) {
                    destinationItem = sourceItem.clone();
                    sourceItem = Item.create( ItemType.AIR );
                } else {
                    destinationItem = sourceItem.clone();
                    destinationItem.setAmount( amount );
                    destinationItem.setStackNetworkId( Item.stackNetworkCount++ );
                    sourceItem.setAmount( sourceItem.getAmount() - amount );
                }
            }
            this.setItem( player, source.getContainer(), source.getSlot(), sourceItem );
            this.setItem( player, destination.getContainer(), destination.getSlot(), destinationItem );

            Item finalSourceItem = sourceItem;
            containerEntryList.add( new ItemStackResponseContainer(
                    source.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    source.getSlot(),
                                    source.getSlot(),
                                    (byte) finalSourceItem.getAmount(),
                                    finalSourceItem.getStackNetworkId(),
                                    finalSourceItem.getDisplayname(),
                                    finalSourceItem.getDurability()
                            )
                    )
            ) );
            Item finalDestinationItem = destinationItem;
            containerEntryList.add( new ItemStackResponseContainer(
                    destination.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    destination.getSlot(),
                                    destination.getSlot(),
                                    (byte) finalDestinationItem.getAmount(),
                                    finalDestinationItem.getStackNetworkId(),
                                    finalDestinationItem.getDisplayname(),
                                    finalDestinationItem.getDurability()
                            )
                    )
            ) );
            return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), containerEntryList ) );
        }
    }

    private List<ItemStackResponse> handleTakeStackRequestAction( Player player, TakeAction actionData, ItemStackRequest itemStackRequest ) {
        int amount = actionData.getCount();
        ItemStackRequestSlotData source = actionData.getSource();
        ItemStackRequestSlotData destination = actionData.getDestination();

        List<ItemStackResponseContainer> entryList = new LinkedList<>();
        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );
        Item destinationItem = this.getItem( player, destination.getContainer(), destination.getSlot() );

        if ( source.getContainer().equals( ContainerSlotType.CREATED_OUTPUT ) ) {
            Inventory sourceInventory = this.getInventory( player, source.getContainer() );
            Inventory destinationInventory = this.getInventory( player, destination.getContainer() );
            InventoryClickEvent inventoryClickEvent = new InventoryClickEvent( sourceInventory, destinationInventory, player, sourceItem, destinationItem, source.getSlot() );
            Server.getInstance().getPluginManager().callEvent( inventoryClickEvent );

            if ( inventoryClickEvent.isCancelled() ) {
                sourceInventory.setItem( source.getSlot(), sourceItem );
                return List.of(
                        new ItemStackResponse( ItemStackResponseStatus.ERROR, itemStackRequest.getRequestId(), Collections.emptyList() ) );
            }

            sourceItem.setStackNetworkId( Item.stackNetworkCount++ );
            if ( !destinationItem.getType().equals( ItemType.AIR ) ) {
                sourceItem.setAmount( Math.min( destinationItem.getAmount() + sourceItem.getAmount(), sourceItem.getMaxStackSize() ) );
            }
            this.setItem( player, destination.getContainer(), destination.getSlot(), sourceItem );
            entryList.add( new ItemStackResponseContainer(
                    destination.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    destination.getSlot(),
                                    destination.getSlot(),
                                    (byte) sourceItem.getAmount(),
                                    sourceItem.getStackNetworkId(),
                                    sourceItem.getDisplayname(),
                                    sourceItem.getDurability()
                            )
                    )
            ) );
            return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), entryList ) );
        } else {
            Inventory sourceInventory = this.getInventory( player, source.getContainer() );
            Inventory destinationInventory = this.getInventory( player, destination.getContainer() );
            InventoryClickEvent inventoryClickEvent = new InventoryClickEvent( sourceInventory, destinationInventory, player, sourceItem, destinationItem, source.getSlot() );
            Server.getInstance().getPluginManager().callEvent( inventoryClickEvent );

            if ( inventoryClickEvent.isCancelled() || ( sourceInventory.getType().equals( InventoryType.ARMOR ) && sourceItem.getEnchantment( EnchantmentType.CURSE_OF_BINDING ) != null ) ) {
                sourceInventory.setItem( source.getSlot(), sourceItem );
                return List.of(
                        new ItemStackResponse( ItemStackResponseStatus.ERROR, itemStackRequest.getRequestId(), Collections.emptyList() ) );
            }

            if ( destinationItem.equals( sourceItem ) && sourceItem.getAmount() > 0 ) {
                sourceItem.setAmount( sourceItem.getAmount() - amount );
                if ( sourceItem.getAmount() <= 0 ) {
                    sourceItem = Item.create( ItemType.AIR );
                }
                destinationItem.setAmount( destinationItem.getAmount() + amount );
            } else if ( destinationItem.getType().equals( ItemType.AIR ) ) {
                if ( sourceItem.getAmount() == amount ) {
                    destinationItem = sourceItem.clone();
                    sourceItem = Item.create( ItemType.AIR );
                } else {
                    destinationItem = sourceItem.clone();
                    destinationItem.setAmount( amount );
                    destinationItem.setStackNetworkId( Item.stackNetworkCount++ );
                    sourceItem.setAmount( sourceItem.getAmount() - amount );
                }
            }

            this.setItem( player, source.getContainer(), source.getSlot(), sourceItem );
            this.setItem( player, destination.getContainer(), destination.getSlot(), destinationItem );

            Item finalSourceItem = sourceItem;
            entryList.add( new ItemStackResponseContainer(
                    source.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    source.getSlot(),
                                    source.getSlot(),
                                    (byte) finalSourceItem.getAmount(),
                                    finalSourceItem.getStackNetworkId(),
                                    finalSourceItem.getDisplayname(),
                                    finalSourceItem.getDurability()
                            )
                    )
            ) );
            Item finalDestinationItem = destinationItem;
            entryList.add( new ItemStackResponseContainer(
                    destination.getContainer(),
                    Collections.singletonList(
                            new ItemStackResponseSlot(
                                    destination.getSlot(),
                                    destination.getSlot(),
                                    (byte) finalDestinationItem.getAmount(),
                                    finalDestinationItem.getStackNetworkId(),
                                    finalDestinationItem.getDisplayname(),
                                    finalDestinationItem.getDurability()
                            )
                    )
            ) );
            return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), entryList ) );
        }
    }

    private List<ItemStackResponse> handleSwapAction( Player player, SwapAction actionData, ItemStackRequest itemStackRequest ) {
        ItemStackRequestSlotData source = actionData.getSource();
        ItemStackRequestSlotData destination = actionData.getDestination();

        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );
        Item destinationItem = this.getItem( player, destination.getContainer(), destination.getSlot() );

        this.setItem( player, source.getContainer(), source.getSlot(), destinationItem );
        this.setItem( player, destination.getContainer(), destination.getSlot(), sourceItem );

        List<ItemStackResponseContainer> containerEntryList = new LinkedList<>();

        containerEntryList.add( new ItemStackResponseContainer(
                destination.getContainer(),
                Collections.singletonList(
                        new ItemStackResponseSlot(
                                destination.getSlot(),
                                destination.getSlot(),
                                (byte) sourceItem.getAmount(),
                                sourceItem.getStackNetworkId(),
                                sourceItem.getDisplayname(),
                                sourceItem.getDurability()
                        )
                )
        ) );

        containerEntryList.add( new ItemStackResponseContainer(
                source.getContainer(),
                Collections.singletonList(
                        new ItemStackResponseSlot(
                                source.getSlot(),
                                source.getSlot(),
                                (byte) destinationItem.getAmount(),
                                destinationItem.getStackNetworkId(),
                                destinationItem.getDisplayname(),
                                destinationItem.getDurability()
                        )
                )
        ) );
        return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), containerEntryList ) );
    }

    private List<ItemStackResponse> handleDestroyAction( Player player, DestroyAction actionData, ItemStackRequest itemStackRequest ) {
        int amount = actionData.getCount();
        ItemStackRequestSlotData source = actionData.getSource();

        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );
        sourceItem.setAmount( sourceItem.getAmount() - amount );
        if ( sourceItem.getAmount() <= 0 ) {
            sourceItem = Item.create( ItemType.AIR );
            this.setItem( player, source.getContainer(), source.getSlot(), sourceItem );
        }
        Item finalSourceItem = sourceItem;

        return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), Collections.singletonList(
                new ItemStackResponseContainer(
                        source.getContainer(),
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        source.getSlot(),
                                        source.getSlot(),
                                        (byte) finalSourceItem.getAmount(),
                                        finalSourceItem.getStackNetworkId(),
                                        finalSourceItem.getDisplayname(),
                                        finalSourceItem.getDurability()
                                )
                        )
                ) )
        ) );
    }

    private List<ItemStackResponse> handleDropItemAction( Player player, DropAction dropStackRequestActionData, ItemStackRequest itemStackRequest ) {
        int amount = dropStackRequestActionData.getCount();
        ItemStackRequestSlotData source = dropStackRequestActionData.getSource();

        Inventory sourceInventory = getInventory( player, source.getContainer() );
        Item sourceItem = this.getItem( player, source.getContainer(), source.getSlot() );

        PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent( player, sourceItem );
        Server.getInstance().getPluginManager().callEvent( playerDropItemEvent );
        if ( playerDropItemEvent.isCancelled() || ( sourceInventory.getType().equals( InventoryType.ARMOR ) && sourceItem.getEnchantment( EnchantmentType.CURSE_OF_BINDING ) != null ) ) {
            sourceInventory.setItem( source.getSlot(), sourceItem );
            return List.of(
                    new ItemStackResponse( ItemStackResponseStatus.ERROR, itemStackRequest.getRequestId(), Collections.emptyList() ) );
        }

        sourceItem.setAmount( sourceItem.getAmount() - amount );

        Item dropItem = sourceItem.clone();
        dropItem.setAmount( amount );

        if ( sourceItem.getAmount() <= 0 ) {
            sourceItem = Item.create( ItemType.AIR );
        }

        this.setItem( player, source.getContainer(), source.getSlot(), sourceItem );

        EntityItem entityItem = player.getWorld().dropItem(
                dropItem,
                player.getLocation().add( 0, player.getEyeHeight(), 0 ),
                player.getLocation().getDirection().multiply( 0.4f, 0.4f, 0.4f ) );
        entityItem.setPlayerHasThrown( player );
        entityItem.spawn();

        Item finalSourceItem = sourceItem;
        return Collections.singletonList( new ItemStackResponse( ItemStackResponseStatus.OK, itemStackRequest.getRequestId(), Collections.singletonList(
                new ItemStackResponseContainer(
                        source.getContainer(),
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        source.getSlot(),
                                        source.getSlot(),
                                        (byte) finalSourceItem.getAmount(),
                                        finalSourceItem.getStackNetworkId(),
                                        finalSourceItem.getDisplayname(),
                                        finalSourceItem.getDurability()
                                )
                        )
                )
        ) ) );
    }

    private ItemStackResponse rejectItemStackRequest( Player player, int requestId, Inventory sourceInventory, Inventory destinationInventory, int sourceSlot, int destinationSlot, Item sourceItem, Item destinationItem ) {
        Server.getInstance().getScheduler().scheduleDelayed( () -> {
            sourceInventory.setItem( sourceSlot, sourceItem );
            sourceInventory.sendContents( sourceSlot, player );
            destinationInventory.setItem( destinationSlot, destinationItem );
            destinationInventory.sendContents( destinationSlot, player );
        }, 20 );
        return new ItemStackResponse( ItemStackResponseStatus.OK, requestId, Arrays.asList(
                new ItemStackResponseContainer(
                        ContainerSlotType.HOTBAR,
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        (byte) sourceSlot,
                                        (byte) sourceSlot,
                                        (byte) sourceItem.getAmount(),
                                        sourceItem.getStackNetworkId(),
                                        sourceItem.getDisplayname(),
                                        sourceItem.getDurability()
                                )
                        )
                ),
                new ItemStackResponseContainer(
                        ContainerSlotType.CURSOR,
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        (byte) destinationSlot,
                                        (byte) destinationSlot,
                                        (byte) destinationItem.getAmount(),
                                        destinationItem.getStackNetworkId(),
                                        destinationItem.getDisplayname(),
                                        destinationItem.getDurability()
                                )
                        )
                )
        ) );
    }

    private Inventory getInventory( Player player, ContainerSlotType containerSlotType ) {
        return switch ( containerSlotType ) {
            case CREATED_OUTPUT -> player.getCreativeItemCacheInventory();
            case CURSOR -> player.getCursorInventory();
            case INVENTORY, HOTBAR, HOTBAR_AND_INVENTORY -> player.getInventory();
            case ARMOR -> player.getArmorInventory();
            case BARREL, BREWING_RESULT, BREWING_FUEL, BREWING_INPUT,
                    FURNACE_FUEL, FURNACE_INGREDIENT, FURNACE_RESULT , BLAST_FURNACE_INGREDIENT,
                    ENCHANTING_INPUT, ENCHANTING_MATERIAL -> player.getCurrentInventory();
            case CRAFTING_INPUT -> player.getCraftingGridInventory();
            case CARTOGRAPHY_ADDITIONAL, CARTOGRAPHY_INPUT, CARTOGRAPHY_RESULT -> player.getCartographyTableInventory();
            case SMITHING_TABLE_INPUT, SMITHING_TABLE_MATERIAL, SMITHING_TABLE_RESULT ->
                    player.getSmithingTableInventory();
            case ANVIL_INPUT, ANVIL_MATERIAL, ANVIL_RESULT -> player.getAnvilInventory();
            case STONECUTTER_INPUT, STONECUTTER_RESULT -> player.getStoneCutterInventory();
            case GRINDSTONE_ADDITIONAL, GRINDSTONE_INPUT, GRINDSTONE_RESULT -> player.getGrindstoneInventory();
            default -> null;
        };
    }

    private Item getItem( Player player, ContainerSlotType containerSlotType, int slot ) {
        return switch ( containerSlotType ) {
            case CREATED_OUTPUT -> player.getCreativeItemCacheInventory().getItem( 0 );
            case CURSOR -> player.getCursorInventory().getItem( slot );
            case OFFHAND -> player.getOffHandInventory().getItem( slot );
            case INVENTORY, HOTBAR, HOTBAR_AND_INVENTORY -> player.getInventory().getItem( slot );
            case ARMOR -> player.getArmorInventory().getItem( slot );
            case  BARREL, BREWING_RESULT, BREWING_FUEL, BREWING_INPUT,
                    FURNACE_FUEL, FURNACE_INGREDIENT, FURNACE_RESULT, BLAST_FURNACE_INGREDIENT,
                    ENCHANTING_INPUT, ENCHANTING_MATERIAL -> player.getCurrentInventory().getItem( slot );
            case CRAFTING_INPUT -> player.getCraftingGridInventory().getItem( slot );
            case CARTOGRAPHY_ADDITIONAL, CARTOGRAPHY_INPUT, CARTOGRAPHY_RESULT ->
                    player.getCartographyTableInventory().getItem( slot );
            case SMITHING_TABLE_INPUT, SMITHING_TABLE_MATERIAL, SMITHING_TABLE_RESULT ->
                    player.getSmithingTableInventory().getItem( slot );
            case ANVIL_INPUT, ANVIL_MATERIAL, ANVIL_RESULT -> player.getAnvilInventory().getItem( slot );
            case STONECUTTER_INPUT, STONECUTTER_RESULT -> player.getStoneCutterInventory().getItem( slot );
            case GRINDSTONE_ADDITIONAL, GRINDSTONE_INPUT, GRINDSTONE_RESULT ->
                    player.getGrindstoneInventory().getItem( slot );
            default -> null;
        };
    }

    private void setItem( Player player, ContainerSlotType containerSlotType, int slot, Item item ) {
        this.setItem( player, containerSlotType, slot, item, true );
    }

    private void setItem( Player player, ContainerSlotType containerSlotType, int slot, Item item, boolean sendContent ) {
        switch ( containerSlotType ) {
            case CURSOR -> {
                player.getCursorInventory().setItem( slot, item, sendContent );
            }
            case OFFHAND -> {
                player.getOffHandInventory().setItem( slot, item, sendContent );
            }
            case INVENTORY, HOTBAR, HOTBAR_AND_INVENTORY -> {
                player.getInventory().setItem( slot, item, sendContent );
            }
            case ARMOR -> {
                player.getArmorInventory().setItem( slot, item, sendContent );
            }
            case BARREL, BREWING_RESULT, BREWING_FUEL, BREWING_INPUT,
                    FURNACE_FUEL, FURNACE_INGREDIENT, FURNACE_RESULT, BLAST_FURNACE_INGREDIENT,
                    ENCHANTING_INPUT, ENCHANTING_MATERIAL -> {
                player.getCurrentInventory().setItem( slot, item, sendContent );
            }
            case CRAFTING_INPUT -> {
                player.getCraftingGridInventory().setItem( slot, item, sendContent );
            }
            case CARTOGRAPHY_ADDITIONAL, CARTOGRAPHY_INPUT, CARTOGRAPHY_RESULT -> {
                player.getCartographyTableInventory().setItem( slot, item, sendContent );
            }
            case SMITHING_TABLE_INPUT, SMITHING_TABLE_MATERIAL, SMITHING_TABLE_RESULT -> {
                player.getSmithingTableInventory().setItem( slot, item, sendContent );
            }
            case ANVIL_INPUT, ANVIL_MATERIAL, ANVIL_RESULT -> {
                player.getAnvilInventory().setItem( slot, item, sendContent );
            }
            case STONECUTTER_INPUT, STONECUTTER_RESULT -> {
                player.getStoneCutterInventory().setItem( slot, item, sendContent );
            }
            case GRINDSTONE_ADDITIONAL, GRINDSTONE_INPUT, GRINDSTONE_RESULT -> {
                player.getGrindstoneInventory().setItem( slot, item, sendContent );
            }
            default -> {
            }
        }
    }
}
