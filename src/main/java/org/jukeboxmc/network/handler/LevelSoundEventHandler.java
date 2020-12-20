package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.LevelSoundEventPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelSoundEventHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        LevelSoundEventPacket levelSoundEventPacket = (LevelSoundEventPacket) packet;

       // System.out.println( levelSoundEventPacket.toString() );
    }
}
