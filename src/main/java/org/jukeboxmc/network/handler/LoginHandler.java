package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.data.LoginData;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler<LoginPacket>{

    @Override
    public void handle( LoginPacket packet, Server server, Player player ) {
        player.getPlayerConnection().setLoginData( new LoginData( packet ) );

        if ( !player.getPlayerConnection().getLoginData().isXboxAuthenticated() && server.isOnlineMode() ) {
            player.getPlayerConnection().disconnect( "You must be logged in with your xbox account." );
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
