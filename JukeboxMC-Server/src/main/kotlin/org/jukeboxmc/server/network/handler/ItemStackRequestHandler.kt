package org.jukeboxmc.server.network.handler

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.SmithingTransformRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.SmithingTrimRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.*
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket
import org.jukeboxmc.api.event.inventory.InventoryClickEvent
import org.jukeboxmc.api.event.player.PlayerDropItemEvent
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.inventory.InventoryType
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.item.JukeboxEntityItem
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.inventory.ContainerInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.PaletteUtil
import org.jukeboxmc.server.util.TrimData
import java.util.*
import java.util.concurrent.TimeUnit


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
            when (action.type) {
                ItemStackRequestActionType.CONSUME -> {
                    val consumeAction = handleConsumeAction(player, action as ConsumeAction)
                    if (!itemEntryMap.containsKey(request.requestId)) {
                        itemEntryMap[request.requestId] = consumeAction
                    } else {
                        itemEntryMap[request.requestId]?.responseSlot?.add(consumeAction.responseSlot[0])
                    }
                }

                ItemStackRequestActionType.TAKE -> {
                    responses.addAll(this.handleTakeRequestAction(player, action as TakeAction, request))
                }

                ItemStackRequestActionType.PLACE -> {
                    responses.addAll(this.handlePlaceAction(player, action as PlaceAction, request))
                }

                ItemStackRequestActionType.DESTROY -> {
                    responses.addAll(this.handleDestroyAction(player, action as DestroyAction, request))
                }

                ItemStackRequestActionType.SWAP -> {
                    responses.addAll(this.handleSwapAction(player, action as SwapAction, request))
                }

                ItemStackRequestActionType.DROP -> {
                    responses.addAll(this.handleDropItemAction(player, action as DropAction, request))
                }

                ItemStackRequestActionType.CRAFT_CREATIVE -> {
                    this.handleCraftCreativeAction(player, action as CraftCreativeAction)
                }

                ItemStackRequestActionType.CRAFT_RECIPE -> {
                    this.handleCraftRecipeAction(player, action as CraftRecipeAction, request)
                }

                ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL -> {
                    responses.addAll(
                        handleCraftRecipeOptionalAction(
                            player,
                            (action as CraftRecipeOptionalAction),
                            request
                        )
                    )
                }

                ItemStackRequestActionType.MINE_BLOCK -> {
                    this.handleMineBlockAction(player, action as MineBlockAction, request)
                }

                ItemStackRequestActionType.CRAFT_LOOM -> {
                    this.handleCraftLoom(player, action as CraftLoomAction, request)
                }

                ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED -> {
                }

                else -> {
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
                itemStackResponsePacket.entries.add(ItemStackResponse(ItemStackResponseStatus.OK, key, value))
            }
        } else {
            itemStackResponsePacket.entries.addAll(responses)
        }
        player.sendPacket(itemStackResponsePacket)
    }

    private fun handleMineBlockAction(
        player: JukeboxPlayer,
        actionData: MineBlockAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val hotBarSlot = actionData.hotbarSlot
        var item = player.getInventory().getItem(actionData.hotbarSlot)

        if (item.getStackNetworkId() != actionData.stackNetworkId) {
            player.getInventory().setItem(hotBarSlot, item)
            return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList()))
        }

        if (item is Durability && item.getDurability() != actionData.predictedDurability) {
            if (item.getDurability() >= item.getMaxDurability() && !item.isUnbreakable()) {
                item = Item.create(ItemType.AIR)
            }
            player.getInventory().setItem(hotBarSlot, item)
        }

        return listOf(
            ItemStackResponse(
                ItemStackResponseStatus.OK,
                itemStackRequest.requestId,
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
                        )
                    )
                )
            )
        )
    }

    private fun handleCraftRecipeOptionalAction(
        player: JukeboxPlayer,
        action: CraftRecipeOptionalAction,
        request: ItemStackRequest
    ): Collection<ItemStackResponse> {
        return emptyList()
    }

    private fun handleCraftLoom(
        player: JukeboxPlayer,
        action: CraftLoomAction,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val loomInventory = player.getLoomInventory()
        val banner = loomInventory.getBanner()
        val material = loomInventory.getMaterial()
        if (banner.getType() != ItemType.AIR && material.getType() != ItemType.AIR) {
            val resultItem = banner.clone().toJukeboxItem()
            val dyeColor = material.getDyeColor()
            val patternCompound = NbtMap.builder()
                .putInt("Color", dyeColor)
                .putString("Pattern", action.patternId)
                .build()

            val compound = if (resultItem.getNbt() != null) banner.getNbt()!! else NbtMap.EMPTY
            if (compound.containsKey("Patterns", NbtType.LIST)) {
                val compoundMap: MutableList<NbtMap> = mutableListOf()
                for (nbtMap in compound.getList("Patterns", NbtType.COMPOUND)) {
                    compoundMap.add(nbtMap)
                }
                compoundMap.add(patternCompound)
                resultItem.setNbt(compound?.toBuilder()
                    ?.putList("Patterns", NbtType.COMPOUND, compoundMap)
                    ?.build()
                )
            } else {
                resultItem.setNbt(compound?.toBuilder()
                    ?.putList("Patterns", NbtType.COMPOUND, patternCompound)
                    ?.build()
                )
            }
            player.getCreativeCacheInventory().setItem(0, resultItem)
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK, request.requestId, listOf(
                        ItemStackResponseContainer(
                            ContainerSlotType.CREATED_OUTPUT,
                            mutableListOf(
                                ItemStackResponseSlot(
                                    0,
                                    0,
                                    resultItem.getAmount(),
                                    resultItem.getStackNetworkId(),
                                    resultItem.getDisplayName(),
                                    resultItem.getDurability()
                                )
                            )
                        )
                    )
                )
            )
        }
        return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, listOf()))
    }

    private fun handleCraftRecipeAction(
        player: JukeboxPlayer,
        action: CraftRecipeAction,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val recipeManager = JukeboxServer.getInstance().getRecipeManager()
        val resultItem: List<JukeboxItem> = recipeManager.getResultItem(action.recipeNetworkId)
        if (resultItem.isNotEmpty()) {
            val jukeboxItem = resultItem[0]
            player.getCreativeCacheInventory().setItem(0, jukeboxItem)
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK, request.requestId, listOf(
                        ItemStackResponseContainer(
                            ContainerSlotType.CREATED_OUTPUT,
                            mutableListOf(
                                ItemStackResponseSlot(
                                    0,
                                    0,
                                    jukeboxItem.getAmount(),
                                    jukeboxItem.getStackNetworkId(),
                                    jukeboxItem.getDisplayName(),
                                    jukeboxItem.getDurability()
                                )
                            )
                        )
                    )
                )
            )
        } else {
            val smithingTrimRecipeData = recipeManager.getCraftingData().filterIsInstance<SmithingTrimRecipeData>()
            val smithingTransformRecipeData =
                recipeManager.getCraftingData().filterIsInstance<SmithingTransformRecipeData>()
            val smithingTableInventory = player.getSmithingTableInventory()
            val base = smithingTableInventory.getBase()
            val addition = smithingTableInventory.getAddition()
            val template = smithingTableInventory.getTemplate()

            if (smithingTrimRecipeData.isNotEmpty() && smithingTrimRecipeData.first().netId == action.recipeNetworkId) {
                val pattern =
                    TrimData.getPattern().find { it.itemName.equals(template.getIdentifier().getFullName()) }
                val materialId =
                    TrimData.getMaterial().find { it.itemName.equals(addition.getIdentifier().getFullName()) }
                if (base.getType() == ItemType.AIR || pattern == null || materialId == null) {
                    return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, listOf()))
                }
                val armorItem = base.clone().toJukeboxItem()
                val trimCompound = NbtMap.builder()
                    .putString("Material", materialId.materialId)
                    .putString("Pattern", pattern.patternId)
                    .build()
                val compound = if (armorItem.getNbt() != null) base.getNbt() else NbtMap.EMPTY
                armorItem.setNbt(compound?.toBuilder()?.putCompound("Trim", trimCompound)?.build())
                player.getCreativeCacheInventory().setItem(0, armorItem)
                return listOf(
                    ItemStackResponse(
                        ItemStackResponseStatus.OK, request.requestId, listOf(
                            ItemStackResponseContainer(
                                ContainerSlotType.CREATED_OUTPUT,
                                mutableListOf(
                                    ItemStackResponseSlot(
                                        0,
                                        0,
                                        armorItem.getAmount(),
                                        armorItem.getStackNetworkId(),
                                        armorItem.getDisplayName(),
                                        armorItem.getDurability()
                                    )
                                )
                            )
                        )
                    )
                )
            } else if (smithingTransformRecipeData.isNotEmpty()) {
                val recipeData = smithingTransformRecipeData.find { it.netId == action.recipeNetworkId }
                if (recipeData != null) {
                    val baseItem = JukeboxItem(recipeData.base.toItem().toBuilder().damage(0).build(), false)
                    val additionItem = JukeboxItem(recipeData.addition.toItem().toBuilder().damage(0).build(), false)
                    val templateItem = JukeboxItem(recipeData.template.toItem().toBuilder().damage(0).build(), false)

                    if (baseItem != base && additionItem != addition && templateItem != template) {
                        return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, listOf()))
                    }

                    val result = JukeboxItem(recipeData.result.toBuilder().build(), true)
                    player.getCreativeCacheInventory().setItem(0, result)
                    return listOf(
                        ItemStackResponse(
                            ItemStackResponseStatus.OK, request.requestId, listOf(
                                ItemStackResponseContainer(
                                    ContainerSlotType.CREATED_OUTPUT,
                                    mutableListOf(
                                        ItemStackResponseSlot(
                                            0,
                                            0,
                                            result.getAmount(),
                                            result.getStackNetworkId(),
                                            result.getDisplayName(),
                                            result.getDurability()
                                        )
                                    )
                                )
                            )
                        )
                    )
                }
            }
        }
        return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, listOf()))
    }

    private fun handleConsumeAction(
        player: JukeboxPlayer,
        action: ConsumeAction
    ): ConsumeActionData {
        val amount = action.count
        val source = action.source
        var sourceItem: Item = this.getItem(player, source.container, source.slot)!!
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

    private fun handleTakeRequestAction(
        player: JukeboxPlayer,
        actionData: TakeAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = actionData.count
        val source = actionData.source
        val destination = actionData.destination
        val entryList: MutableList<ItemStackResponseContainer> = LinkedList()
        var sourceItem = getItem(player, source.container, source.slot)
        var destinationItem = getItem(player, destination.container, destination.slot)
        if (sourceItem == null || destinationItem == null) {
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
            )
        }
        if (source.container == ContainerSlotType.CREATED_OUTPUT) {
            val sourceInventory = getInventory(player, source.container)
            val destinationInventory = getInventory(player, destination.container)
            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
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
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
                )
            }
            sourceItem.toJukeboxItem().setStackNetworkId(JukeboxItem.stackNetworkCount++)
            if (destinationItem.getType() != ItemType.AIR) {
                sourceItem.setAmount((destinationItem.getAmount() + sourceItem.getAmount()).coerceAtMost(sourceItem.getMaxStackSize()))
            }
            setItem(player, destination.container, destination.slot, sourceItem)
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
                    )
                )
            )
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    itemStackRequest.requestId,
                    entryList
                )
            )
        } else {
            val sourceInventory = getInventory(player, source.container)
            val destinationInventory = getInventory(player, destination.container)
            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
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
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
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
            this.setItem(player, source.container, source.slot, sourceItem)
            this.setItem(player, destination.container, destination.slot, destinationItem)
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
                    )
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
                    )
                )
            )
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    itemStackRequest.requestId,
                    entryList
                )
            )
        }
    }

    private fun handlePlaceAction(
        player: JukeboxPlayer,
        actionData: PlaceAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = actionData.count
        val source = actionData.source
        val destination = actionData.destination
        val containerEntryList: MutableList<ItemStackResponseContainer> = LinkedList()
        var sourceItem = getItem(player, source.container, source.slot)
        var destinationItem = getItem(player, destination.container, destination.slot)

        if (sourceItem == null || destinationItem == null) {
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
            )
        }

        if (source.container == ContainerSlotType.CREATED_OUTPUT) {
            val sourceInventory = getInventory(player, source.container)
            val destinationInventory = getInventory(player, destination.container)

            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
                )
            }

            val inventoryClickEvent = InventoryClickEvent(
                sourceInventory,
                destinationInventory, player, sourceItem, destinationItem, source.slot
            )
            JukeboxServer.getInstance().getPluginManager().callEvent(inventoryClickEvent)
            if (inventoryClickEvent.isCancelled()) {
                sourceInventory.setItem(source.slot, sourceItem)
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
                )
            }
            sourceItem.toJukeboxItem().setStackNetworkId(JukeboxItem.stackNetworkCount++)
            if (destinationItem.getType() != ItemType.AIR) {
                sourceItem.setAmount((destinationItem.getAmount() + sourceItem.getAmount()).coerceAtMost(sourceItem.getMaxStackSize()))
            }
            this.setItem(player, destination.container, destination.slot, sourceItem)
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
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    itemStackRequest.requestId,
                    containerEntryList
                )
            )
        } else {
            val sourceInventory = getInventory(player, source.container)
            val destinationInventory = getInventory(player, destination.container)

            if (sourceInventory == null || destinationInventory == null) {
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
                )
            }

            val inventoryClickEvent = InventoryClickEvent(
                sourceInventory,
                destinationInventory, player, sourceItem, destinationItem, source.slot
            )
            JukeboxServer.getInstance().getPluginManager().callEvent(inventoryClickEvent)
            if (inventoryClickEvent.isCancelled() || sourceInventory.getType() == InventoryType.ARMOR && sourceItem.hasEnchantment(
                    EnchantmentType.CURSE_OF_BINDING
                )
            ) {
                sourceInventory.setItem(source.slot, sourceItem)
                return listOf(
                    ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
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
            this.setItem(player, source.container, source.slot, sourceItem)
            this.setItem(player, destination.container, destination.slot, destinationItem)

            val finalSourceItem = sourceItem
            containerEntryList.add(
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
            val finalDestinationItem = destinationItem
            containerEntryList.add(
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
                    )
                )
            )
            return listOf(
                ItemStackResponse(
                    ItemStackResponseStatus.OK,
                    itemStackRequest.requestId,
                    containerEntryList
                )
            )
        }
    }

    private fun handleDestroyAction(
        player: JukeboxPlayer,
        actionData: DestroyAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = actionData.count
        val source = actionData.source
        var sourceItem = getItem(player, source.container, source.slot)
        sourceItem!!.setAmount(sourceItem.getAmount() - amount)
        if (sourceItem.getAmount() <= 0) {
            sourceItem = Item.create(ItemType.AIR)
            setItem(player, source.container, source.slot, sourceItem)
        }
        val finalSourceItem = sourceItem
        return listOf(
            ItemStackResponse(
                ItemStackResponseStatus.OK, itemStackRequest.requestId, listOf(
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

    private fun handleDropItemAction(
        player: JukeboxPlayer,
        dropStackRequestActionData: DropAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val amount = dropStackRequestActionData.count
        val source = dropStackRequestActionData.source
        val sourceInventory = getInventory(player, source.container)
        var sourceItem = getItem(player, source.container, source.slot)!!
        sourceItem.setAmount(amount)

        val playerDropItemEvent = PlayerDropItemEvent(player, sourceItem)
        JukeboxServer.getInstance().getPluginManager().callEvent(playerDropItemEvent)
        if (playerDropItemEvent.isCancelled() || sourceInventory!!.getType() == InventoryType.ARMOR && sourceItem.getEnchantment(
                EnchantmentType.CURSE_OF_BINDING
            ) != null
        ) {
            sourceInventory!!.setItem(source.slot, sourceItem)
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
            )
        }
        sourceItem.setAmount(sourceItem.getAmount())
        if (sourceItem.getAmount() <= 0) {
            sourceItem = Item.create(ItemType.AIR)
        }
        this.setItem(player, source.container, source.slot, sourceItem)

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
                ItemStackResponseStatus.OK, itemStackRequest.requestId, listOf(
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

    private fun handleSwapAction(
        player: JukeboxPlayer,
        actionData: SwapAction,
        itemStackRequest: ItemStackRequest
    ): List<ItemStackResponse> {
        val source = actionData.source
        val destination = actionData.destination
        val sourceItem = getItem(player, source.container, source.slot)
        val destinationItem = getItem(player, destination.container, destination.slot)

        if (sourceItem == null || destinationItem == null) {
            return listOf(
                ItemStackResponse(ItemStackResponseStatus.ERROR, itemStackRequest.requestId, emptyList())
            )
        }

        setItem(player, source.container, source.slot, destinationItem)
        setItem(player, destination.container, destination.slot, sourceItem)
        val containerEntryList: MutableList<ItemStackResponseContainer> = LinkedList()
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
        return listOf(ItemStackResponse(ItemStackResponseStatus.OK, itemStackRequest.requestId, containerEntryList))
    }

    private fun handleCraftCreativeAction(player: JukeboxPlayer, actionData: CraftCreativeAction) {
        val itemData: ItemData = PaletteUtil.getCreativeItems()[actionData.creativeItemNetworkId - 1]
        try {
            val item = JukeboxItem.create(itemData)
            item.setAmount(item.getMaxStackSize())
            player.getCreativeCacheInventory().setItem(0, item)
        } catch (e: Exception) {
            e.printStackTrace()
            player.sendMessage("§cThe item could not be created")
            JukeboxServer.getInstance().getLogger().error("The item could not be created: $itemData")
        }
    }

    private fun getInventory(player: JukeboxPlayer, containerSlotType: ContainerSlotType): Inventory? {
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

    private fun getItem(player: JukeboxPlayer, containerSlotType: ContainerSlotType, slot: Int): Item? {
        return when (containerSlotType) {
            ContainerSlotType.CREATED_OUTPUT -> player.getCreativeCacheInventory().getItem(0)
            else -> this.getInventory(player, containerSlotType)?.getItem(slot)
        }
    }

    private fun setItem(
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

    fun Item.getDyeColor(): Int {
        return when(this.getType()) {
            ItemType.WHITE_DYE -> 15
            ItemType.ORANGE_DYE -> 14
            ItemType.MAGENTA_DYE -> 13
            ItemType.LIGHT_BLUE_DYE -> 12
            ItemType.YELLOW_DYE -> 11
            ItemType.LIME_DYE -> 10
            ItemType.PINK_DYE -> 9
            ItemType.GRAY_DYE -> 8
            ItemType.LIGHT_GRAY_DYE -> 7
            ItemType.CYAN_DYE -> 6
            ItemType.PURPLE_DYE -> 5
            ItemType.BLUE_DYE -> 4
            ItemType.BROWN_DYE -> 3
            ItemType.GREEN_DYE -> 2
            ItemType.RED_DYE -> 1
            ItemType.BLACK_DYE -> 0
            else -> 0
        }
    }

    /*
        fun Item.getDyeColor(): Int {
        return when(this.getType()) {
            ItemType.WHITE_DYE -> 0
            ItemType.ORANGE_DYE -> 1
            ItemType.MAGENTA_DYE -> 2
            ItemType.LIGHT_BLUE_DYE -> 3
            ItemType.YELLOW_DYE -> 4
            ItemType.LIME_DYE -> 5
            ItemType.PINK_DYE -> 6
            ItemType.GRAY_DYE -> 7
            ItemType.LIGHT_GRAY_DYE -> 8
            ItemType.CYAN_DYE -> 9
            ItemType.PURPLE_DYE -> 10
            ItemType.BLUE_DYE -> 11
            ItemType.BROWN_DYE -> 12
            ItemType.GREEN_DYE -> 13
            ItemType.RED_DYE -> 14
            ItemType.BLACK_DYE -> 15
            else -> 0
        }
    }
     */

    data class ConsumeActionData(
        val containerSlotType: ContainerSlotType,
        val responseSlot: MutableList<ItemStackResponseSlot>
    )
}