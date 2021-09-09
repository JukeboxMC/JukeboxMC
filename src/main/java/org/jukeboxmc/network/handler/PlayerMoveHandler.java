package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.player.PlayerMoveEvent;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.network.packet.type.PlayerMoveMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler<PlayerMovePacket> {

    @Override
    public void handle( PlayerMovePacket packet, Server server, Player player ) {
        Location toLocation = new Location( player.getLocation().getWorld(), packet.getX(), packet.getY() - player.getEyeHeight(), packet.getZ(), packet.getYaw(), packet.getPitch(), player.getDimension() );

        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent( player, player.getLocation(), toLocation );
        Server.getInstance().getPluginManager().callEvent( playerMoveEvent );

        if ( playerMoveEvent.isCancelled() ) {
            playerMoveEvent.setTo( playerMoveEvent.getFrom() );
        }

        player.setLocation( toLocation );
        player.setOnGround( packet.isOnGround() );

        Location to = playerMoveEvent.getTo();
        Location fromLocation = player.getLocation();

        if ( to.getX() != fromLocation.getX() || to.getY() != fromLocation.getY() || to.getZ() != fromLocation.getZ() || to.getWorld() != fromLocation.getWorld()
                || to.getYaw() != fromLocation.getYaw() || to.getPitch() != fromLocation.getPitch() ) {
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
                    onlinePlayer.movePlayer( player, PlayerMoveMode.NORMAL );
                }
            }
        }

        Location from = playerMoveEvent.getFrom();
        if ( to.getWorld() != from.getWorld() || from.getBlockX() != to.getBlockX()
                || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ() ) {
            Block block = from.getBlock();
            block.leaveBlock( player );

            block = to.getBlock();
            block.enterBlock( player );
        }
    }
}
