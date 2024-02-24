package org.jukeboxmc.server.network.handler

import io.netty.buffer.Unpooled
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackChunkDataPacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackChunkRequestPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class ResourcePackChunkRequestHandler : PacketHandler<ResourcePackChunkRequestPacket> {

    override fun handle(packet: ResourcePackChunkRequestPacket, server: JukeboxServer, player: JukeboxPlayer) {
        val resourcePack = server.getResourcePackManager().getResourcePack(packet.packId)

        if (resourcePack != null) {
            val size = 1048576
            val resourcePackChunkDataPacket = ResourcePackChunkDataPacket()
            resourcePackChunkDataPacket.packId = resourcePack.getUuid()
            resourcePackChunkDataPacket.chunkIndex = packet.chunkIndex
            resourcePackChunkDataPacket.progress = (size * packet.chunkIndex).toLong()
            resourcePackChunkDataPacket.data = Unpooled.wrappedBuffer(resourcePack.getChunk(resourcePackChunkDataPacket.progress.toInt(), size))

            player.sendPacket(resourcePackChunkDataPacket)
        }
    }
}