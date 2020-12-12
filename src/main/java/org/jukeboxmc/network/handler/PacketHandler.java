package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface PacketHandler {

    void handle( Packet packet, Player player );

}
