package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.LoginPacket;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.Protocol;
import org.jukeboxmc.network.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.network.packet.type.PlayStatus;
import org.jukeboxmc.player.Player;

import java.util.Locale;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler<LoginPacket> {

    @Override
    public void handle( LoginPacket packet, Server server, Player player ) {
        player.setName( packet.getUsername() );
        player.setXuid( packet.getXuid() );
        player.setUUID( packet.getUuid() );
        player.setSkin( packet.getSkin() );
        player.setNameTagAlwaysVisible( true );
        player.setNameTag( packet.getUsername() );
        player.setProtocol( packet.getProtocol() );
        player.setDeviceInfo( packet.getDeviceInfo() );
        player.setMinecraftVersion( packet.getGameVersion() );
        player.setLocale( packet.getLanguageCode() != null ? Locale.forLanguageTag( packet.getLanguageCode().replace( "_", "-" ) ) : Locale.US );

        if ( !packet.isXboxAuthenticated() && server.isOnlineMode() ) {
            player.disconnect( "You must be loged in with your xbox account." );
            return;
        }

        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        PlayStatus status = PlayStatus.LOGIN_SUCCESS;

        if ( packet.getProtocol() < Protocol.CURRENT_PROTOCOL ) {
            status = PlayStatus.LOGIN_FAILED_CLIENT_OUTDATED;
        }

        if ( packet.getProtocol() > Protocol.CURRENT_PROTOCOL ) {
            status = PlayStatus.LOGIN_FAILED_SERVER_OUTDATED;
        }

        if ( server.getOnlinePlayers().size() >= server.getMaxPlayers() ) {
            status = PlayStatus.LOGIN_FAILED_SERVER_FULL;
        }
        playStatusPacket.setStatus( status );
        player.sendPacket( playStatusPacket );

        if ( status.equals( PlayStatus.LOGIN_SUCCESS ) ) {
            ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
            resourcePacksInfoPacket.setForceAccept( false );
            resourcePacksInfoPacket.setForceServerPacks( false );
            resourcePacksInfoPacket.setScripting( false );
            player.sendPacket( resourcePacksInfoPacket );
        }
    }
}
