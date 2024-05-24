package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.EncodingSettings
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ConsumeAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket
import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.inventory.ContainerInventory
import org.jukeboxmc.server.network.stackaction.StackAction
import org.jukeboxmc.server.network.stackaction.StackActionHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.*


class ItemStackRequestHandler : PacketHandler<ItemStackRequestPacket> {

    override fun handle(packet: ItemStackRequestPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.getSession().peer.codecHelper.encodingSettings = EncodingSettings.builder()
            .maxListSize(Int.MAX_VALUE)
            .maxByteArraySize(Int.MAX_VALUE)
            .maxNetworkNBTSize(Int.MAX_VALUE)
            .maxItemNBTSize(Int.MAX_VALUE)
            .maxStringLength(Int.MAX_VALUE)
            .build()
        val responses: MutableList<ItemStackResponse> = mutableListOf()
        for (request in packet.requests) {
            this.handleItemStackRequest(request, player, responses)
        }
    }

    fun handleItemStackRequest(
        request: ItemStackRequest,
        player: JukeboxPlayer,
        responses: MutableList<ItemStackResponse>
    ) {
        val itemEntryMap: MutableMap<Int, ConsumeActionData> = mutableMapOf()
        for (action in request.actions) {
            if (action.type == ItemStackRequestActionType.CONSUME) {
                val consumeAction = handleConsumeAction(player, action as ConsumeAction)
                if (!itemEntryMap.containsKey(request.requestId)) {
                    itemEntryMap[request.requestId] = consumeAction
                } else {
                    itemEntryMap[request.requestId]?.responseSlot?.add(consumeAction.responseSlot[0])
                }
            } else {
                val stackAction = StackActionHandler.getPacketHandler(action.type)
                if (stackAction != null && stackAction.isType<StackAction<ItemStackRequestAction>>()) {
                    val responseList =
                        stackAction.asType<StackAction<ItemStackRequestAction>>().handle(action, player, this, request)
                    if (responseList.isNotEmpty()) {
                        responses.addAll(responseList)
                    }
                } else if (action.type != ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED){
                    JukeboxServer.getInstance().getLogger()
                        .info("Unhandelt Action: " + action.javaClass.simpleName + " : " + action.type)
                }
            }
        }
        val itemStackResponsePacket = ItemStackResponsePacket()
        val containerEntryMap: MutableMap<Int, MutableList<ItemStackResponseContainer>> = HashMap()
        if (itemEntryMap.isNotEmpty()) {
            for (respons in responses) {
                containerEntryMap[respons.requestId] = respons.containers
            }
            if (containerEntryMap.containsKey(request.requestId)) {
                containerEntryMap[request.requestId]?.add(
                    0,
                    ItemStackResponseContainer(
                        itemEntryMap[request.requestId]!!.containerSlotType,
                        itemEntryMap[request.requestId]!!.responseSlot
                    )
                )
            }
            for ((key, value) in containerEntryMap) {
                if (value.isNotEmpty()) {
                    itemStackResponsePacket.entries.add(ItemStackResponse(ItemStackResponseStatus.OK, key, value))
                } else {
                    itemStackResponsePacket.entries.add(
                        ItemStackResponse(
                            ItemStackResponseStatus.ERROR,
                            key,
                            emptyList()
                        )
                    )
                }
            }
        } else {
            itemStackResponsePacket.entries.addAll(responses)
        }
        player.sendPacket(itemStackResponsePacket)
    }

    private fun handleConsumeAction(
        player: JukeboxPlayer,
        action: ConsumeAction
    ): ConsumeActionData {
        val amount = action.count
        val source = action.source
        var sourceItem: Item = this.getItem(player, source.container, source.slot) ?: return ConsumeActionData(
            source.container,
            mutableListOf()
        )
        sourceItem.setAmount(sourceItem.getAmount() - amount)
        if (sourceItem.getAmount() <= 0) {
            sourceItem = Item.create(ItemType.AIR)
        }

        this.setItem(player, source.container, source.slot, sourceItem)
        val containerEntryList: MutableList<ItemStackResponseSlot> = LinkedList()
        containerEntryList.add(
            ItemStackResponseSlot(
                source.slot,
                source.slot,
                sourceItem.getAmount(),
                sourceItem.getStackNetworkId(),
                sourceItem.getDisplayName(),
                sourceItem.getDurability()
            )
        )
        return ConsumeActionData(source.container, containerEntryList)
    }

    fun getInventory(player: JukeboxPlayer, containerSlotType: ContainerSlotType): Inventory? {
        return when (containerSlotType) {
            ContainerSlotType.CREATED_OUTPUT -> player.getCreativeCacheInventory()
            ContainerSlotType.CURSOR -> player.getCursorInventory()
            ContainerSlotType.INVENTORY,
            ContainerSlotType.HOTBAR,
            ContainerSlotType.HOTBAR_AND_INVENTORY -> player.getInventory()

            ContainerSlotType.ARMOR -> player.getArmorInventory()
            ContainerSlotType.OFFHAND -> player.getCurrentInventory()
            ContainerSlotType.CARTOGRAPHY_ADDITIONAL,
            ContainerSlotType.CARTOGRAPHY_INPUT,
            ContainerSlotType.CARTOGRAPHY_RESULT -> player.getCartographyTableInventory()

            ContainerSlotType.SMITHING_TABLE_INPUT,
            ContainerSlotType.SMITHING_TABLE_RESULT,
            ContainerSlotType.SMITHING_TABLE_TEMPLATE,
            ContainerSlotType.SMITHING_TABLE_MATERIAL -> player.getSmithingTableInventory()

            ContainerSlotType.ANVIL_INPUT,
            ContainerSlotType.ANVIL_MATERIAL,
            ContainerSlotType.ANVIL_RESULT -> player.getAnvilInventory()

            ContainerSlotType.STONECUTTER_INPUT,
            ContainerSlotType.STONECUTTER_RESULT -> player.getStoneCutterInventory()

            ContainerSlotType.GRINDSTONE_ADDITIONAL,
            ContainerSlotType.GRINDSTONE_INPUT,
            ContainerSlotType.GRINDSTONE_RESULT -> player.getGrindstoneInventory()

            ContainerSlotType.CRAFTING_INPUT -> player.getCraftingGridInventory()

            ContainerSlotType.LOOM_DYE,
            ContainerSlotType.LOOM_INPUT,
            ContainerSlotType.LOOM_MATERIAL,
            ContainerSlotType.LOOM_RESULT -> player.getLoomInventory()

            ContainerSlotType.BARREL,
            ContainerSlotType.BREWING_FUEL,
            ContainerSlotType.BREWING_INPUT,
            ContainerSlotType.BREWING_RESULT,
            ContainerSlotType.FURNACE_FUEL,
            ContainerSlotType.FURNACE_INGREDIENT,
            ContainerSlotType.FURNACE_RESULT,
            ContainerSlotType.BLAST_FURNACE_INGREDIENT,
            ContainerSlotType.ENCHANTING_INPUT,
            ContainerSlotType.ENCHANTING_MATERIAL -> player.getCurrentInventory()

            else -> {
                return null
            }
        }
    }

    fun getItem(player: JukeboxPlayer, containerSlotType: ContainerSlotType, slot: Int): Item? {
        return when (containerSlotType) {
            ContainerSlotType.CREATED_OUTPUT -> player.getCreativeCacheInventory().getItem(0)
            else -> this.getInventory(player, containerSlotType)?.getItem(slot)
        }
    }

    public fun setItem(
        player: JukeboxPlayer,
        containerSlotType: ContainerSlotType,
        slot: Int,
        item: Item,
        sendContent: Boolean = true
    ) {
        when (containerSlotType) {
            ContainerSlotType.CURSOR -> {
                player.getCursorInventory().setCursorItem(item)
            }

            ContainerSlotType.OFFHAND -> {
                player.getOffHandInventory().setOffHandItem(item)
            }

            ContainerSlotType.CREATED_OUTPUT -> {
                player.getCreativeCacheInventory().setItem(slot, item)
            }

            else -> (this.getInventory(player, containerSlotType) as ContainerInventory).setItem(
                slot,
                item,
                sendContent
            )
        }
    }

    data class ConsumeActionData(
        val containerSlotType: ContainerSlotType,
        val responseSlot: MutableList<ItemStackResponseSlot>
    )
}