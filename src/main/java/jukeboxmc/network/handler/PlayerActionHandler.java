package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.event.player.*;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.PlayerActionPacket;
import org.jukeboxmc.network.packet.type.PlayStatus;
import org.jukeboxmc.network.packet.type.PlayerAction;
import org.jukeboxmc.player.Player;

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
                player.getPlayerConnection().sendPacket( new PlayStatusPacket( PlayStatus.PLAYER_SPAWN ) );
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
            case START_BREAK:
                Block block = player.getWorld().getBlock( packet.getPosition() );

                PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player,
                        block.getBlockType().equals( BlockType.AIR ) ? PlayerInteractEvent.Action.LEFT_CLICK_AIR :
                                PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, player.getInventory().getItemInHand(), block );

                Server.getInstance().getPluginManager().callEvent( playerInteractEvent );
                break;
            default:
                break;
        }
    }
}