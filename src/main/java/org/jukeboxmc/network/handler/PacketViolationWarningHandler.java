package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.PacketViolationWarningPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketViolationWarningHandler implements PacketHandler<PacketViolationWarningPacket> {

    @Override
    public void handle(@NotNull PacketViolationWarningPacket packet, @NotNull Server server, Player player ) {
        server.getLogger().info( packet.toString() );
    }
}
