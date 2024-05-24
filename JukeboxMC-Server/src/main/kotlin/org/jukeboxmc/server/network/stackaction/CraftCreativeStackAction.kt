package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.CraftCreativeAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.PaletteUtil

class CraftCreativeStackAction : StackAction<CraftCreativeAction> {

    override fun handle(
        action: CraftCreativeAction,
        player: JukeboxPlayer,
        handler: ItemStackRequestHandler,
        request: ItemStackRequest
    ): List<ItemStackResponse> {
        val itemData: ItemData = PaletteUtil.getCreativeItems()[action.creativeItemNetworkId - 1]
        try {
            val item = JukeboxItem.create(itemData)
            item.setAmount(item.getMaxStackSize())
            player.getCreativeCacheInventory().setItem(0, item)
        } catch (e: Exception) {
            e.printStackTrace()
            player.sendMessage("§cThe item could not be created")
            JukeboxServer.getInstance().getLogger().error("The item could not be created: $itemData")
        }
        return emptyList()
    }
}