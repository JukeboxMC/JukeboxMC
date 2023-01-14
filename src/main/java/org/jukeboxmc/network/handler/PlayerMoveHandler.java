package org.jukeboxmc.network.handler;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerMoveEvent;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler<MovePlayerPacket> {

    @Override
    public void handle( MovePlayerPacket packet, Server server, Player player ) {
        if ( !player.isSpawned() ) {
            return;
        }
        if ( player.getTeleportLocation() != null ) {
            return;
        }
        Location toLocation = new Location( player.getWorld(), new Vector( packet.getPosition().sub( 0, player.getEyeHeight(), 0 ) ), packet.getRotation().getY(), packet.getRotation().getX(), player.getLocation().getDimension() );
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
            Chunk fromChunk = player.getLastLocation().getLoadedChunk();
            if ( fromChunk == null ) return;
            Chunk toChunk = player.getLoadedChunk();
            if ( toChunk == null ) return;
            if ( toChunk.getX() != fromChunk.getX() || toChunk.getZ() != fromChunk.getZ() ) {
                fromChunk.removeEntity( player );
                toChunk.addEntity( player );
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
                        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                            player.exhaust( ( 0.6f * distance + swimmingValue ) );
                        }
                    } else {
                        if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                            player.exhaust( ( 0.1f * distance + swimmingValue ) );
                        }
                    }
                }
            }

            for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
                if ( onlinePlayer != player ) {
                    if ( player.isSpawned() && onlinePlayer.isSpawned() ) {
                        this.move( player, onlinePlayer );
                    }
                }
            }
        }
    }

    private void move( Player target, Player player ) {
        MoveEntityAbsolutePacket moveAbsolutePacket = new MoveEntityAbsolutePacket();
        moveAbsolutePacket.setRuntimeEntityId( target.getEntityId() );
        moveAbsolutePacket.setPosition( target.getLocation().toVector3f().add( 0, target.getEyeHeight(), 0 ) );
        moveAbsolutePacket.setRotation( Vector3f.from( target.getLocation().getPitch(), target.getLocation().getYaw(), target.getLocation().getYaw() ) );
        moveAbsolutePacket.setOnGround( target.isOnGround() );
        moveAbsolutePacket.setTeleported( false );
        player.getPlayerConnection().sendPacket( moveAbsolutePacket );
    }
}
