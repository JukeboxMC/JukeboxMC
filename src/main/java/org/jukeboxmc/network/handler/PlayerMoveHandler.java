package org.jukeboxmc.network.handler;

import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerMovePacket playerMovePacket = (PlayerMovePacket) packet;
        Chunk fromChunk = player.getChunk();

        Location toLocation = new Location( player.getLocation().getWorld(), playerMovePacket.getX(), playerMovePacket.getY() - player.getEyeHeight(), playerMovePacket.getZ(), playerMovePacket.getYaw(), playerMovePacket.getPitch() );
        player.setHeadYaw( playerMovePacket.getHeadYaw() );
        player.setLocation( toLocation );
        player.setOnGround( playerMovePacket.isOnGround() );
        Chunk toChunk = player.getChunk();

        if ( fromChunk.getChunkX() != toChunk.getChunkX() || fromChunk.getChunkZ() != toChunk.getChunkZ() ) {
            fromChunk.removePlayer( player );
            toChunk.addPlayer( player );
        }

        for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
            if ( onlinePlayer != player ) {
                onlinePlayer.getPlayerConnection().movePlayer( player, PlayerMovePacket.Mode.NORMAL );
            }
        }

        player.sendTip( " §bChunkX§7: §f" + player.getChunkX() + " §bChunkZ§7: §f" + player.getChunkZ() );
    }
}
