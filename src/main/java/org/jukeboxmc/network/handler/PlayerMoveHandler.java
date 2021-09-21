package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.entity.Entity;
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

        player.setLastLocation( player.getLocation() );
        player.setLocation( toLocation );
        player.setOnGround( packet.isOnGround() );

        Location to = playerMoveEvent.getTo();
        Location fromLocation = player.getLocation();

        if ( to.getX() != fromLocation.getX() || to.getY() != fromLocation.getY() || to.getZ() != fromLocation.getZ() || to.getWorld() != fromLocation.getWorld()
                || to.getYaw() != fromLocation.getYaw() || to.getPitch() != fromLocation.getPitch() ) {
            player.teleport( playerMoveEvent.getFrom() );
        } else {
            Chunk fromChunk = player.getLastLocation().getChunk();
            Chunk toChunk = player.getChunk();
            if ( toChunk.getChunkX() != fromChunk.getChunkX() || toChunk.getChunkZ() != fromChunk.getChunkZ() ) {
                fromChunk.removeEntity( player );
                toChunk.addEntity( player );

                for ( Entity entity : toChunk.getEntities() ) {
                    if ( !( entity instanceof Player ) && !entity.isClosed() ) {
                        entity.spawn( player );
                    }
                }
            }

            float moveX = toLocation.getX() - player.getLastLocation().getX();
            float moveZ = toLocation.getZ() - player.getLastLocation().getZ();

            float distance = (float) Math.sqrt( moveX * moveX + moveZ * moveZ );

            if ( distance >= 0.01 ) {
                float swimmingValue = player.isSwimming() || player.isInWater() ? 0.15f * distance : 0f;
                if ( swimmingValue != 0 ) {
                    distance = 0;
                }
                if ( player.isOnGround() ) {
                    if ( player.isSprinting() ) {
                        player.exhaust( ( 0.6f * distance + swimmingValue ) );
                    } else {
                        player.exhaust( ( 0.1f * distance + swimmingValue ) );
                    }
                }
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
