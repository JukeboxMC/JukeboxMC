package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerMoveEvent;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MovePlayerHandler implements PacketHandler<MovePlayerPacket> {

    @Override
    public void handle( MovePlayerPacket packet, Server server, Player player ) {
        Location toLocation = new Location( player.getWorld(), new Vector( packet.getPosition().sub( 0, player.getEyeHeight(), 0 ) ), packet.getRotation().getY() , packet.getRotation().getX(), player.getDimension() );
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

        if ( to.getX() != fromLocation.getX() || to.getY() != fromLocation.getY() || to.getZ() != fromLocation.getZ() || to.getWorld() != fromLocation.getWorld() || to.getYaw() != fromLocation.getYaw() || to.getPitch() != fromLocation.getPitch() ) {
            player.teleport( playerMoveEvent.getFrom() );
        } else {
            Chunk fromChunk = player.getLastLocation().getChunk();
            Chunk toChunk = player.getChunk();
            if ( toChunk.getChunkX() != fromChunk.getChunkX() || toChunk.getChunkZ() != fromChunk.getChunkZ() ) {
                fromChunk.removeEntity( player );
                toChunk.addEntity( player );

                for ( Entity entity : toChunk.getEntities() ) {
                    if ( !( entity instanceof Player ) && !entity.isClosed() && player.getLastLocation().getWorld().equals( player.getWorld() ) ) {
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
                    onlinePlayer.movePlayer( player, MovePlayerPacket.Mode.NORMAL );
                }
            }
        }
    }
}
