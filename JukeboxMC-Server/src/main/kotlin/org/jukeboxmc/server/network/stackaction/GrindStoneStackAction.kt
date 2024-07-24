package org.jukeboxmc.server.network.stackaction

import it.unimi.dsi.fastutil.Pair
import it.unimi.dsi.fastutil.objects.ObjectIntMutablePair
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftGrindstoneAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.event.inventory.PrepareGrindstoneEvent
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.inventory.JukeboxGrindstoneInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.ceil
import kotlin.random.Random

class GrindStoneStackAction : StackAction<CraftGrindstoneAction> {

    override fun handle(
        action: CraftGrindstoneAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val inventory = player.getGrindstoneInventory()
        val firstItem = inventory.getInput()
        val secondItem = inventory.getAdditional()

        if (firstItem.getType() == ItemType.AIR && secondItem.getType() == ItemType.AIR) {
            return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList()))
        }

        val pair = handleGrindstoneResult(inventory)
            ?: return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList()))

        val exp = pair.right()
        val resultItem = pair.left()

        val prepareGrindstoneEvent = PrepareGrindstoneEvent(inventory, player, firstItem, secondItem, resultItem, exp.toFloat())
        JukeboxServer.getInstance().getPluginManager().callEvent(prepareGrindstoneEvent)
        if (prepareGrindstoneEvent.isCancelled()) {
            player.closeInventory(inventory)
            return listOf(ItemStackResponse(ItemStackResponseStatus.ERROR, request.requestId, emptyList()))
        }
        player.addExperience(prepareGrindstoneEvent.getExperience())
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
                        ),
                        FullContainerName(ContainerSlotType.CREATED_OUTPUT, 0)
                    )
                )
            )
        )
    }

    private fun handleGrindstoneResult(inventory: JukeboxGrindstoneInventory): Pair<JukeboxItem, Int>? {
        var firstItem = inventory.getInput()
        var secondItem = inventory.getAdditional()
        val resultPair: Pair<JukeboxItem, Int> = ObjectIntMutablePair.of(JukeboxItem.AIR, 0)
        if (firstItem.getType() != ItemType.AIR && secondItem.getType() != ItemType.AIR && firstItem.getType() != secondItem.getType()) {
            return null
        }
        if (firstItem.getType() == ItemType.AIR) {
            val air = firstItem
            firstItem = secondItem
            secondItem = air
        }
        if (firstItem.getType() == ItemType.AIR) {
            return null
        }
        if (firstItem.getType() == ItemType.ENCHANTED_BOOK) {
            if (secondItem.getType() == ItemType.AIR) {
                resultPair.left(Item.create(ItemType.BOOK, firstItem.getAmount()).toJukeboxItem())
                resultPair.right(this.recalculateResultExperience(inventory))
            } else {
                return null
            }
            return resultPair
        }
        val resultExperience = this.recalculateResultExperience(inventory)
        val result = firstItem.clone().toJukeboxItem()
        result.removeEnchantments()

        if (secondItem.getType() != ItemType.AIR && secondItem is Durability && firstItem is Durability && firstItem.getMaxDurability() > 0) {
            val first = firstItem.getMaxDurability() - firstItem.getDurability()
            val second = secondItem.getMaxDurability() - secondItem.getDurability()
            val reduction = first + second + firstItem.getMaxDurability() * 5 / 100
            val resultingDamage = (firstItem.getMaxDurability() - reduction + 1).coerceAtLeast(0)
            result.setDurability(resultingDamage)
        }
        resultPair.left(result.toJukeboxItem())
        resultPair.right(resultExperience)
        return resultPair
    }

    private fun recalculateResultExperience(inventory: JukeboxGrindstoneInventory): Int {
        val firstItem = inventory.getInput()
        val secondItem = inventory.getAdditional()
        if (firstItem.getEnchantments().isEmpty() && secondItem.getEnchantments().isEmpty()) {
            return 0
        }
        var resultExperience = listOf(firstItem, secondItem)
            .flatMap { item ->
                when {
                    item.getType() == ItemType.AIR -> emptyList()
                    item.getAmount() == 1 -> item.getEnchantments().toList()
                    else -> List(item.getAmount()) { item.getEnchantments().toList() }.flatten()
                }
            }.sumOf { it.getMinCost(it.getLevel()) }

        resultExperience = Random.nextInt(
            ceil(resultExperience.toDouble() / 2).toInt(),
            resultExperience + 1
        )
        return resultExperience
    }
}