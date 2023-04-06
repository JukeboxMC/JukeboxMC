package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.AnvilDamagePacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnvilDamageHandler implements PacketHandler<AnvilDamagePacket>{

    @Override
    public void handle( AnvilDamagePacket packet, Server server, Player player ) {
    }
}
