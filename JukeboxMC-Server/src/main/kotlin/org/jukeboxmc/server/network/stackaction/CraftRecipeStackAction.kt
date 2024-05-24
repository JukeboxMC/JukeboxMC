package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.SmithingTransformRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.SmithingTrimRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftRecipeAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.TrimData

class CraftRecipeStackAction : StackAction<CraftRecipeAction> {

    override fun handle(
        action: CraftRecipeAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
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
}