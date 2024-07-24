package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.MineBlockAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer

class MineBlockStackAction : StackAction<MineBlockAction> {

    override fun handle(
        action: MineBlockAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val hotBarSlot = action.hotbarSlot
        var item = player.getInventory().getItem(action.hotbarSlot)

        if (item.getStackNetworkId() != action.stackNetworkId) {
            player.getInventory().setItem(hotBarSlot, item)
            return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList()))
        }

        if (item is Durability && item.getDurability() != action.predictedDurability) {
            if (item.getDurability() >= item.getMaxDurability() && !item.isUnbreakable()) {
                item = Item.create(ItemType.AIR)
            }
            player.getInventory().setItem(hotBarSlot, item)
        }

        return listOf(
            ItemStackResponse(
                ItemStackResponseStatus.OK,
                request.requestId,
                listOf(
                    ItemStackResponseContainer(
                        ContainerSlotType.HOTBAR,
                        listOf(
                            ItemStackResponseSlot(
                                hotBarSlot,
                                hotBarSlot,
                                item.getAmount(),
                                item.getStackNetworkId(),
                                item.getDisplayName(),
                                item.getDurability()
                            )
                        ),
                        FullContainerName(ContainerSlotType.HOTBAR, 0)
                    )
                )
            )
        )
    }
}