package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerToggleSneakEvent;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerActionPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerActionHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerActionPacket playerActionPacket = (PlayerActionPacket) packet;
        PlayerActionPacket.Action action = playerActionPacket.getAction();

        switch ( action ) {
            case START_SNEAK:
                PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( player, true );
                Server.getInstance().getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.getPlayerConnection().sendMetadata();
                } else {
                    player.setSneaking( true );
                }
                break;
            case STOP_SNEAK:
                playerToggleSneakEvent = new PlayerToggleSneakEvent( player, false );
                Server.getInstance().getPluginManager().callEvent( playerToggleSneakEvent );
                if ( playerToggleSneakEvent.isCancelled() ) {
                    player.getPlayerConnection().sendMetadata();
                } else {
                    player.setSneaking( false );
                }
                break;
            case START_SPRINT:
                player.setSprinting( true );
                break;
            case STOP_SPRINT:
                player.setSprinting( false );
                break;
            default:
                break;
        }
    }
}
