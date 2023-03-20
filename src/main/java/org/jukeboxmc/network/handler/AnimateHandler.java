package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.AnimatePacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AnimateHandler implements PacketHandler<AnimatePacket>{

    @Override
    public void handle(@NotNull AnimatePacket packet, Server server, @NotNull Player player ) {
        if ( packet.getAction() == AnimatePacket.Action.SWING_ARM ) {
            Set<Player> players = player.getServer().getOnlinePlayers().stream().filter( p -> !p.equals( player ) ).collect( Collectors.toSet() );
            if ( !players.isEmpty() ) {
                player.getServer().broadcastPacket( players, packet );
            }
        }
    }
}
