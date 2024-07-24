package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.PlaceAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.event.inventory.InventoryClickEvent
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.*

class PlaceStackAction: StackAction<PlaceAction> {

    override fun handle(
        action: PlaceAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = action.count
        val source = action.source
        val destination = action.destination
        val entryList: MutableList<ItemStackResponseContainer> = LinkedList()
        var sourceItem = handler.getItem(player, source.container, source.slot)
        var destinationItem = handler.getItem(player, destination.container, destination.slot)
        if (sourceItem == null || destinationItem == null) {
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
            )
        }
        if (source.container == ContainerSlotType.CREATED_OUTPUT) {
            val sourceInventory = handler.getInventory(player, source.container)
            val destinationInventory = handler.getInventory(player, destination.container)
            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
                )
            }
            sourceItem.setAmount(amount.coerceAtMost(sourceItem.getMaxStackSize()))
            val inventoryClickEvent = InventoryClickEvent(
                sourceInventory,
                destinationInventory,
                player,
                sourceItem,
                destinationItem,
                source.slot
            )
            JukeboxServer.getInstance().getPluginManager().callEvent(inventoryClickEvent)
            if (inventoryClickEvent.isCancelled()) {
                sourceInventory.setItem(source.slot, sourceItem)
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
                )
            }
            sourceItem.toJukeboxItem().setStackNetworkId(JukeboxItem.stackNetworkCount++)
            if (destinationItem.getType() != ItemType.AIR) {
                sourceItem.setAmount((destinationItem.getAmount() + sourceItem.getAmount()).coerceAtMost(sourceItem.getMaxStackSize()))
            }
            handler.setItem(player, destination.container, destination.slot, sourceItem)
            entryList.add(
                ItemStackResponseContainer(
                    destination.container, listOf(
                        ItemStackResponseSlot(
                            destination.slot,
                            destination.slot,
                            sourceItem.getAmount(),
                            sourceItem.getStackNetworkId(),
                            sourceItem.getDisplayName(),
                            sourceItem.getDurability()
                        )
                    ),
                    FullContainerName(destination.container, 0)
                )
            )
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    request.requestId,
                    entryList
                )
            )
        } else {
            val sourceInventory = handler.getInventory(player, source.container)
            val destinationInventory = handler.getInventory(player, destination.container)
            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
                )
            }
            val inventoryClickEvent = InventoryClickEvent(
                sourceInventory,
                destinationInventory,
                player,
                sourceItem,
                destinationItem,
                source.slot
            )
            JukeboxServer.getInstance().getPluginManager().callEvent(inventoryClickEvent)
            if (inventoryClickEvent.isCancelled() || sourceInventory.getType() == InventoryType.ARMOR && sourceItem.hasEnchantment(
                    EnchantmentType.CURSE_OF_BINDING
                )
            ) {
                sourceInventory.setItem(source.slot, sourceItem)
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
                )
            }
            if (destinationItem.isSimilar(sourceItem) && sourceItem.getAmount() > 0) {
                sourceItem.setAmount(sourceItem.getAmount() - amount)
                if (sourceItem.getAmount() <= 0) {
                    sourceItem = Item.create(ItemType.AIR)
                }
                destinationItem.setAmount(destinationItem.getAmount() + amount)
            } else if (destinationItem.getType() == ItemType.AIR) {
                if (sourceItem.getAmount() == amount) {
                    destinationItem = sourceItem.clone()
                    sourceItem = Item.create(ItemType.AIR)
                } else {
                    destinationItem = sourceItem.clone()
                    destinationItem.setAmount(amount)
                    destinationItem.toJukeboxItem().setStackNetworkId(JukeboxItem.stackNetworkCount++)
                    sourceItem.setAmount(sourceItem.getAmount() - amount)
                }
            }
            handler.setItem(player, source.container, source.slot, sourceItem)
            handler.setItem(player, destination.container, destination.slot, destinationItem)
            val finalSourceItem = sourceItem
            entryList.add(
                ItemStackResponseContainer(
                    source.container, listOf(
                        ItemStackResponseSlot(
                            source.slot,
                            source.slot,
                            finalSourceItem.getAmount(),
                            finalSourceItem.getStackNetworkId(),
                            finalSourceItem.getDisplayName(),
                            finalSourceItem.getDurability()
                        )
                    ),
                    FullContainerName(source.container, 0)
                )
            )
            val finalDestinationItem = destinationItem
            entryList.add(
                ItemStackResponseContainer(
                    destination.container, listOf(
                        ItemStackResponseSlot(
                            destination.slot,
                            destination.slot,
                            finalDestinationItem.getAmount(),
                            finalDestinationItem.getStackNetworkId(),
                            finalDestinationItem.getDisplayName(),
                            finalDestinationItem.getDurability()
                        )
                    ),
                    FullContainerName(destination.container, 0)
                )
            )
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    request.requestId,
                    entryList
                )
            )
        }
    }
}