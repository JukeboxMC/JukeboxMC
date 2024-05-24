package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.DropAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.event.player.PlayerDropItemEvent
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.item.JukeboxEntityItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.concurrent.TimeUnit

class DropStackAction : StackAction<DropAction> {

    override fun handle(
        action: DropAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = action.count
        val source = action.source
        val sourceInventory = handler.getInventory(player, source.container)
        var sourceItem = handler.getItem(player, source.container, source.slot) ?: return listOf(
            ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
        )
        sourceItem.setAmount(amount)

        val playerDropItemEvent = PlayerDropItemEvent(player, sourceItem)
        JukeboxServer.getInstance().getPluginManager().callEvent(playerDropItemEvent)
        if (playerDropItemEvent.isCancelled() || sourceInventory!!.getType() == InventoryType.ARMOR && sourceItem.getEnchantment(
                EnchantmentType.CURSE_OF_BINDING
            ) != null
        ) {
            sourceInventory!!.setItem(source.slot, sourceItem)
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList())
            )
        }
        sourceItem.setAmount(sourceItem.getAmount())
        if (sourceItem.getAmount() <= 0) {
            sourceItem = Item.create(ItemType.AIR)
        }
        handler.setItem(player, source.container, source.slot, sourceItem)

        val entityItem = JukeboxEntityItem()
        entityItem.setItem(playerDropItemEvent.getItem())
        entityItem.setLocation(player.getLocation().add(0F, player.getEyeHeight(), 0F).clone())
        entityItem.setMotion(player.getLocation().getDirection().multiply(0.4F, 0.4F, 0.4f))
        entityItem.setThrower(player)
        entityItem.setPickupDelay(1, TimeUnit.SECONDS)
        entityItem.spawn()

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