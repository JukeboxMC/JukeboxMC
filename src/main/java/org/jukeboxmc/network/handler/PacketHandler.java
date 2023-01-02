package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface PacketHandler<T extends BedrockPacket> {

    void handle( T packet, Server server, Player player );
}

