package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftLoomAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer

class LoomStackAction : StackAction<CraftLoomAction> {

    override fun handle(
        action: CraftLoomAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
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
                resultItem.setNbt(
                    compound?.toBuilder()
                        ?.putList("Patterns", NbtType.COMPOUND, compoundMap)
                        ?.build()
                )
            } else {
                resultItem.setNbt(
                    compound?.toBuilder()
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

    private fun Item.getDyeColor(): Int {
        return when (this.getType()) {
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
}