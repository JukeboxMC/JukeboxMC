package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.ViolationWarningPacket;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ViolationWarningPacketHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ViolationWarningPacket violationWarningPacket = (ViolationWarningPacket) packet;

        System.out.println(violationWarningPacket.toString());
    }
}