package org.jukeboxmc.server.network.stackaction

import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse
import org.jukeboxmc.server.network.handler.ItemStackRequestHandler
import org.jukeboxmc.server.player.JukeboxPlayer

interface StackAction<T : ItemStackRequestAction> {

    fun handle(action: T, player: JukeboxPlayer, handler: ItemStackRequestHandler, request: ItemStackRequest): List<ItemStackResponse>

}
