package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.BlockPickRequestPacket
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.fromVector3i
import org.jukeboxmc.server.player.JukeboxPlayer

class BlockPickRequestHandler : PacketHandler<BlockPickRequestPacket> {

    override fun handle(packet: BlockPickRequestPacket, server: JukeboxServer, player: JukeboxPlayer) {
        val position = Vector().fromVector3i(packet.blockPosition, player.getDimension())
        val pickedBlock = player.getWorld().getBlock(position)

        if (player.getGameMode() == GameMode.CREATIVE) {
            val item = pickedBlock.toItem()
            if (item.getType() == ItemType.AIR) {
                server.getLogger().warn("User try to pick air")
                return
            }
            if (!player.getInventory().contains(item.getType())) {
                player.getInventory().addItem(item)
            }
        }
    }
}