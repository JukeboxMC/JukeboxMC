package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.SetLocalPlayerAsInitializedPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        SetLocalPlayerAsInitializedPacket initializedPacket = (SetLocalPlayerAsInitializedPacket) packet;


    }
}
