package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.data.LoginData;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler<LoginPacket> {

    @Override
    public void handle( LoginPacket packet, Server server, Player player ) {
        if ( player.getPlayerConnection().isLoggedIn() ) {
            player.getPlayerConnection().close( "Player is already logged in." );
            return;
        }
        int protocolVersion = packet.getProtocolVersion();
        int currentProtocol = Network.CODEC.getProtocolVersion();

        if ( protocolVersion != currentProtocol ) {
            player.getPlayerConnection().sendPlayStatus( protocolVersion > currentProtocol ? PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD : PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD );
            return;
        }

        player.getPlayerConnection().getSession().setPacketCodec( Network.CODEC );

        player.getPlayerConnection().setLoginData( new LoginData( packet ) );

        if ( !player.getPlayerConnection().getLoginData().isXboxAuthenticated() && server.isOnlineMode() ) {
            player.getPlayerConnection().close( "You must be logged in with your xbox account." );
            return;
        }

        player.getPlayerConnection().sendPlayStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );

        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        resourcePacksInfoPacket.setForcedToAccept( false );
        resourcePacksInfoPacket.setForcingServerPacksEnabled( false );
        resourcePacksInfoPacket.setScriptingEnabled( false );
        player.getPlayerConnection().sendPacket( resourcePacksInfoPacket );
    }
}
