package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.event.player.*;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.LevelEventPacket;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.PlayerActionPacket;
import org.jukeboxmc.network.packet.type.PlayStatus;
import org.jukeboxmc.network.packet.type.PlayerAction;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelEvent;
import org.jukeboxmc.world.Particle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerActionHandler implements PacketHandler<PlayerActionPacket> {

    @Override
    public void handle( PlayerActionPacket packet, Server server, Player player ) {
        PlayerAction playerAction = packet.getAction();

        switch ( playerAction ) {
            case DIMENSION_CHANGE_ACK:
                player.sendPacket( new PlayStatusPacket( PlayStatus.PLAYER_SPAWN ) );
                break;
            case START_SNEAK:
                PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSneaking( true );
                }
                break;
            case STOP_SNEAK:
                playerToggleSneakEvent = new PlayerToggleSneakEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSneaking( false );
                }
                break;
            case START_SPRINT:
                PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSprintEvent );
                if ( playerToggleSprintEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSprinting( true );
                }
                break;
            case STOP_SPRINT:
                playerToggleSprintEvent = new PlayerToggleSprintEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSprintEvent );
                if ( playerToggleSprintEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSprinting( false );
                }
                break;
            case START_SWIMMING:
                PlayerToggleSwimEvent playerToggleSwimEvent = new PlayerToggleSwimEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSwimEvent );
                if ( playerToggleSwimEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSwimming( true );
                }
                break;
            case STOP_SWIMMING:
                playerToggleSwimEvent = new PlayerToggleSwimEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSwimEvent );
                if ( playerToggleSwimEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setSwimming( false );
                }
                break;
            case START_GLIDE:
                PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleGlideEvent );
                if ( playerToggleGlideEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setGliding( true );
                }
                break;
            case STOP_GLIDE:
                playerToggleGlideEvent = new PlayerToggleGlideEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleGlideEvent );
                if ( playerToggleGlideEvent.isCancelled() ) {
                    player.updateMetadata();
                } else {
                    player.setGliding( false );
                }
                break;
            case JUMP:
                if ( player.isSprinting() ) {
                    player.exhaust( 0.8f );
                } else {
                    player.exhaust( 0.2f );
                }
                break;
            case RESPAWN:
                player.respawn();
                break;
            case START_BREAK:
                long currentBreakTime = System.currentTimeMillis();
                Block startBreakBlock = player.getWorld().getBlock( packet.getPosition() );

                PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player,
                        startBreakBlock.getBlockType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.LEFT_CLICK_AIR :
                                PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, player.getInventory().getItemInHand(), startBreakBlock );

                Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

                if ( player.getGameMode().equals( GameMode.SURVIVAL ) ) {
                    if ( ( player.getLasBreakPosition().equals( packet.getPosition() ) && ( currentBreakTime - player.getLastBreakTime() ) < 10 ) || packet.getPosition().distanceSquared( player.getLocation() ) > 100 ) {
                        return;
                    }

                    double breakTime = Math.ceil( startBreakBlock.getBreakTime( player.getInventory().getItemInHand(), player ) * 20 );
                    if ( breakTime > 0 ) {
                        LevelEventPacket levelEventPacket = new LevelEventPacket();
                        levelEventPacket.setLevelEventId( LevelEvent.BLOCK_START_BREAK.getId() );
                        levelEventPacket.setPosition( packet.getPosition() );
                        levelEventPacket.setData( (int) ( 65535 / breakTime ) );
                        player.getWorld().sendChunkPacket( packet.getPosition().getChunkX(), packet.getPosition().getChunkZ(), levelEventPacket );
                    }
                }

                player.setBreakingBlock( true );
                player.setLasBreakPosition( packet.getPosition() );
                player.setLastBreakTime( currentBreakTime );
                break;
            case STOP_BREAK:
            case ABORT_BREAK:
                LevelEventPacket levelEventPacket = new LevelEventPacket();
                levelEventPacket.setLevelEventId( LevelEvent.BLOCK_STOP_BREAK.getId() );
                levelEventPacket.setPosition( packet.getPosition() );
                levelEventPacket.setData( 0 );
                player.getWorld().sendChunkPacket( packet.getPosition().getChunkX(), packet.getPosition().getChunkZ(), levelEventPacket );
                player.setBreakingBlock( false );
                break;
            case CONTINUE_BREAK:
                if ( player.isBreakingBlock() ) {
                    Item itemInHand = player.getInventory().getItemInHand();
                    Block continueBlockBreak = player.getWorld().getBlock( packet.getPosition() );
                    player.getWorld().spawnParticle( null, Particle.CRACK_BLOCK, packet.getPosition(), continueBlockBreak.getRuntimeId() | packet.getBlockFace().ordinal() << 24 );
                }
                break;
            default:
                break;
        }
    }
}