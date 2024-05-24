package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.SwapAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer

class SwapStackAction : StackAction<SwapAction> {

    override fun handle(
        action: SwapAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val source = action.source
        val destination = action.destination
        val sourceItem = handler.getItem(player, source.container, source.slot)
        val destinationItem = handler.getItem(player, destination.container, destination.slot)

        if (sourceItem == null || destinationItem == null) {
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
            )
        }

        handler.setItem(player, source.container, source.slot, destinationItem)
        handler.setItem(player, destination.container, destination.slot, sourceItem)

        val containerEntryList: MutableList<ItemStackResponseContainer> = mutableListOf()
        containerEntryList.add(
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
                )
            )
        )
        containerEntryList.add(
            ItemStackResponseContainer(
                source.container, listOf(
                    ItemStackResponseSlot(
                        source.slot,
                        source.slot,
                        destinationItem.getAmount(),
                        destinationItem.getStackNetworkId(),
                        destinationItem.getDisplayName(),
                        destinationItem.getDurability()
                    )
                )
            )
        )
        return listOf(ItemStackResponse(ItemStackResponseStatus.OK, request.requestId, containerEntryList))
    }
}