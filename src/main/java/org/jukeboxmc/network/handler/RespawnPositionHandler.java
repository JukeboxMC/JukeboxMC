package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.RespawnPositionPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RespawnPositionHandler implements PacketHandler<RespawnPositionPacket> {

    @Override
    public void handle( RespawnPositionPacket packet, Server server, Player player ) {
        if ( packet.getRespawnState().equals( RespawnPositionPacket.RespawnState.CLIENT_READY_TO_SPAWN ) ) {
            RespawnPositionPacket respawnPositionPacket = new RespawnPositionPacket();
            respawnPositionPacket.setEntityId( player.getEntityId() );
            respawnPositionPacket.setRespawnState( RespawnPositionPacket.RespawnState.READY_TO_SPAWN );
            respawnPositionPacket.setPosition( player.getSpawnLocation() );
            player.getPlayerConnection().sendPacket( respawnPositionPacket );
        }
    }
}
