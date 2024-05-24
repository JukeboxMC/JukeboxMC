package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.DestroyAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer

class DestroyStackAction : StackAction<DestroyAction> {

    override fun handle(
        action: DestroyAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = action.count
        val source = action.source
        var sourceItem = handler.getItem(player, source.container, source.slot)
            ?: return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
            )
        sourceItem.setAmount(sourceItem.getAmount() - amount)
        if (sourceItem.getAmount() <= 0) {
            sourceItem = Item.create(ItemType.AIR)
            handler.setItem(player, source.container, source.slot, sourceItem)
        }
        val finalSourceItem = sourceItem
        return listOf(
            ItemStackResponse(
                ItemStackResponseStatus.OK, request.requestId, listOf(
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
                        )
                    )
                )
            )
        )
    }
}