package org.jukeboxmc.network.handler;

import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerMovePacket playerMovePacket = (PlayerMovePacket) packet;
        Location from = player.getLocation();
        Location to = new Location( player.getLocation().getWorld(), playerMovePacket.getX(), playerMovePacket.getY() - 1.62f, playerMovePacket.getZ(), playerMovePacket.getYaw(), playerMovePacket.getPitch() );
        player.setLocation( to );

        for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
            if ( onlinePlayer != player ) {
                onlinePlayer.getPlayerConnection().movePlayer( player, PlayerMovePacket.Mode.NORMAL );
            }
        }

        player.sendTip( "X: " + player.getFloorX() + " Y: " + player.getFloorY() + " Z: " + player.getFloorZ() + " ChunkX: " + player.getChunkX() + " ChunkZ: " + player.getChunkZ() );
    }
}
