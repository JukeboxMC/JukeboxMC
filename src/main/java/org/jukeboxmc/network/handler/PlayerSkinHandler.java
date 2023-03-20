package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.skin.Skin;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerSkinHandler implements PacketHandler<PlayerSkinPacket> {

    @Override
    public void handle(@NotNull PlayerSkinPacket packet, Server server, @NotNull Player player ) {
        player.setSkin( Skin.fromNetwork( packet.getSkin() ) );
    }
}
