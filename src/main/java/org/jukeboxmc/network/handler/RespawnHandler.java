package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RespawnHandler implements PacketHandler<RespawnPacket>{

    @Override
    public void handle( RespawnPacket packet, Server server, Player player ) {
        if ( packet.getState().equals( RespawnPacket.State.CLIENT_READY ) ) {
            RespawnPacket respawnPositionPacket = new RespawnPacket();
            respawnPositionPacket.setRuntimeEntityId( player.getEntityId() );
            respawnPositionPacket.setState( RespawnPacket.State.SERVER_READY);
            respawnPositionPacket.setPosition( player.getSpawnLocation().toVector3f() );
            player.sendPacket( respawnPositionPacket );
        }
    }
}
