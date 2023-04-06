package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.PacketViolationWarningPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketViolationWarningHandler implements PacketHandler<PacketViolationWarningPacket> {

    @Override
    public void handle( PacketViolationWarningPacket packet, Server server, Player player ) {
        server.getLogger().info( packet.toString() );
    }
}
