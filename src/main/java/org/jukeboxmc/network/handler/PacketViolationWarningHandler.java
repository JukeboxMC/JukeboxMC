package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.PacketViolationWarningPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketViolationWarningHandler implements PacketHandler<PacketViolationWarningPacket> {

    @Override
    public void handle( PacketViolationWarningPacket packet, Server server, Player player ) {
        server.getLogger().info( "[ViolationWarning] Type: " + packet.getType().name() + " Severity: " + packet.getSeverity().name() + " PacketId: 0x" + Integer.toHexString( packet.getPacketId() ) + " Context: " + packet.getContext() );
    }
}
