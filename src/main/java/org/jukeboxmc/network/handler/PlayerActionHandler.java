package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.event.player.*;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Particle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerActionHandler implements PacketHandler<PlayerActionPacket> {

    @Override
    public void handle( PlayerActionPacket packet, Server server, Player player ) {
        final Vector lasBreakPosition = new Vector( packet.getBlockPosition() );
        switch ( packet.getAction() ) {
            case START_SNEAK -> {
                PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( player, true );
                server.getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSneaking( true );
                }
            }
            case STOP_SNEAK -> {
                PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSneaking( false );
                }
            }
            case START_SPRINT -> {
                PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSprintEvent );
                if ( playerToggleSprintEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSprinting( true );
                }
            }
            case STOP_SPRINT -> {
                PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSprintEvent );
                if ( playerToggleSprintEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSprinting( false );
                }
            }
            case START_SWIMMING -> {
                PlayerToggleSwimEvent playerToggleSwimEvent = new PlayerToggleSwimEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSwimEvent );
                if ( playerToggleSwimEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSwimming( true );
                }
            }
            case STOP_SWIMMING -> {
                PlayerToggleSwimEvent playerToggleSwimEvent = new PlayerToggleSwimEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSwimEvent );
                if ( playerToggleSwimEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSwimming( false );
                }
            }
            case START_GLIDE -> {
                PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleGlideEvent );
                if ( playerToggleGlideEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setGliding( true );
                }
            }
            case STOP_GLIDE -> {
                PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleGlideEvent );
                if ( playerToggleGlideEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setGliding( false );
                }
            }
            case JUMP -> {
                if ( player.isSprinting() ) {
                    player.exhaust( 0.8f );
                } else {
                    player.exhaust( 0.2f );
                }
            }
            case START_BREAK -> {
                long currentBreakTime = System.currentTimeMillis();
                Block startBreakBlock = player.getWorld().getBlock( lasBreakPosition );

                PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player,
                        startBreakBlock.getBlockType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.LEFT_CLICK_AIR :
                                PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, player.getInventory().getItemInHand(), startBreakBlock );

                Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    if ( ( player.getLasBreakPosition().equals( lasBreakPosition ) && ( currentBreakTime - player.getLastBreakTime() ) < 10 ) || lasBreakPosition.distanceSquared( player.getLocation() ) > 100 ) {
                        return;
                    }

                    double breakTime = Math.ceil( startBreakBlock.getBreakTime( player.getInventory().getItemInHand(), player ) * 20 );
                    if ( breakTime > 0 ) {
                        LevelEventPacket levelEventPacket = new LevelEventPacket();
                        levelEventPacket.setType( LevelEventType.BLOCK_START_BREAK );
                        levelEventPacket.setPosition( packet.getBlockPosition().toFloat() );
                        levelEventPacket.setData( (int) ( 65535 / breakTime ) );
                        player.getWorld().sendChunkPacket( lasBreakPosition.getChunkX(), lasBreakPosition.getChunkZ(), levelEventPacket );
                    }
                }

                player.setBreakingBlock( true );
                player.setLasBreakPosition( lasBreakPosition );
                player.setLastBreakTime( currentBreakTime );
            }
            case STOP_BREAK, ABORT_BREAK -> {
                LevelEventPacket levelEventPacket = new LevelEventPacket();
                levelEventPacket.setType( LevelEventType.BLOCK_STOP_BREAK );
                levelEventPacket.setPosition( packet.getBlockPosition().toFloat() );
                levelEventPacket.setData( 0 );
                player.getWorld().sendChunkPacket( lasBreakPosition.getChunkX(), lasBreakPosition.getChunkZ(), levelEventPacket );
                player.setBreakingBlock( false );
            }
            case CONTINUE_BREAK -> {
                if ( player.isBreakingBlock() ) {
                    Item itemInHand = player.getInventory().getItemInHand();
                    Block continueBlockBreak = player.getWorld().getBlock( lasBreakPosition );
                    player.getWorld().spawnParticle( null, Particle.CRACK_BLOCK, lasBreakPosition, continueBlockBreak.getRuntimeId() | packet.getFace() << 24 );
                }
            }
            case RESPAWN -> {
                player.respawn();
            }
        }
    }
}
