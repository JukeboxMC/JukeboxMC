package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDataHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        BlockEntityDataPacket entityDataPacket = (BlockEntityDataPacket) packet;

        System.out.println( entityDataPacket.toString() );
    }
}
