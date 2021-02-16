package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.player.PlayerMoveEvent;
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

        Location toLocation = new Location( player.getLocation().getWorld(), playerMovePacket.getX(), playerMovePacket.getY() - player.getEyeHeight(), playerMovePacket.getZ(), playerMovePacket.getHeadYaw(), playerMovePacket.getYaw(), playerMovePacket.getPitch() );

        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent( player, player.getLocation(), toLocation );
        Server.getInstance().getPluginManager().callEvent( playerMoveEvent );

        if ( playerMoveEvent.isCancelled() ) {
            playerMoveEvent.setTo( playerMoveEvent.getFrom() );
        }

        player.setLocation( toLocation );
        player.setOnGround( playerMovePacket.isOnGround() );

        Location to = playerMoveEvent.getTo();
        Location fromLocation = player.getLocation();

        if ( to.getX() != fromLocation.getX() || to.getY() != fromLocation.getY() || to.getZ() != fromLocation.getZ() || to.getWorld() != fromLocation.getWorld()
                || to.getHeadYaw() != fromLocation.getHeadYaw() || to.getYaw() != fromLocation.getYaw() || to.getPitch() != fromLocation.getPitch() ) {
            player.teleport( playerMoveEvent.getFrom() );
        } else {
            Chunk fromChunk = fromLocation.getChunk();
            Chunk toChunk = player.getChunk();

            if ( fromChunk.getChunkX() != toChunk.getChunkX() || fromChunk.getChunkZ() != toChunk.getChunkZ() ) {
                fromChunk.removeEntity( player );
                toChunk.addEntity( player );
            }

            for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
                if ( onlinePlayer != player ) {
                    onlinePlayer.getPlayerConnection().movePlayer( player, PlayerMovePacket.Mode.NORMAL );
                }
            }
        }

        Location from = playerMoveEvent.getFrom();
        if ( to.getWorld() != from.getWorld() || from.getFloorX() != to.getFloorX()
                || from.getFloorY() != to.getFloorY() || from.getFloorZ() != to.getFloorZ() ) {
            Block block = from.getBlock();
            block.leaveBlock();

            block = to.getBlock();
            block.enterBlock();
        }
    }
}