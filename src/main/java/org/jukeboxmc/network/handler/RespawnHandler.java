package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RespawnHandler implements PacketHandler<RespawnPacket> {

    @Override
    public void handle(@NotNull RespawnPacket packet, Server server, @NotNull Player player ) {
        if ( packet.getState().equals( RespawnPacket.State.CLIENT_READY ) ) {
            RespawnPacket respawnPositionPacket = new RespawnPacket();
            respawnPositionPacket.setRuntimeEntityId( player.getEntityId() );
            respawnPositionPacket.setState( RespawnPacket.State.SERVER_READY );
            respawnPositionPacket.setPosition( player.getSpawnLocation().toVector3f() );
            player.getPlayerConnection().sendPacket( respawnPositionPacket );
        }
    }
}
