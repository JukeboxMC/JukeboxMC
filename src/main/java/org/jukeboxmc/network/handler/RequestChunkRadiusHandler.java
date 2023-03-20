package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler<RequestChunkRadiusPacket> {

    @Override
    public void handle(@NotNull RequestChunkRadiusPacket packet, Server server, @NotNull Player player ) {
        player.setChunkRadius( packet.getRadius() );
    }
}
