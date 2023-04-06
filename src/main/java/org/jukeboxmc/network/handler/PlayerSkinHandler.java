package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.skin.Skin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerSkinHandler implements PacketHandler<PlayerSkinPacket> {

    @Override
    public void handle( PlayerSkinPacket packet, Server server, Player player ) {
        player.setSkin( Skin.fromNetwork( packet.getSkin() ) );
    }
}
