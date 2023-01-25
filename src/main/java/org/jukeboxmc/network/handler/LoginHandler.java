package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerLoginEvent;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.data.LoginData;
import org.jukeboxmc.resourcepack.ResourcePack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler<LoginPacket>{

    @Override
    public void handle( LoginPacket packet, Server server, Player player ) {
        player.getPlayerConnection().setLoginData( new LoginData( packet ) );

        if ( !player.getPlayerConnection().getLoginData().isXboxAuthenticated() && server.isOnlineMode() ) {
            player.kick( "You must be logged in with your xbox account." );
            return;
        }

        PlayerLoginEvent playerLoginEvent = new PlayerLoginEvent( player );
        if ( Server.getInstance().getOnlinePlayers().size() >= Server.getInstance().getMaxPlayers() && !playerLoginEvent.canJoinFullServer()) {
            playerLoginEvent.setCancelled( true );
            playerLoginEvent.setKickReason( "Server is full." );
        }

        if ( playerLoginEvent.isCancelled() ) {
            player.kick( playerLoginEvent.getKickReason() );
        }
        Server.getInstance().getPluginManager().callEvent( playerLoginEvent );

        player.getPlayerConnection().sendPlayStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );

        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        for( ResourcePack resourcePack : server.getResourcePackManager().retrieveResourcePacks() ) {
            resourcePacksInfoPacket.getBehaviorPackInfos().add( 
                new ResourcePacksInfoPacket.Entry(
                    resourcePack.getUuid().toString(),
                    resourcePack.getVersion(),
                    resourcePack.getSize(),
                    "",
                    "",
                    resourcePack.getUuid().toString(),
                    false,
                    false 
                )
            );
        }
        resourcePacksInfoPacket.setForcedToAccept( false );
        resourcePacksInfoPacket.setForcingServerPacksEnabled( false );
        resourcePacksInfoPacket.setScriptingEnabled( false );
        player.getPlayerConnection().sendPacket( resourcePacksInfoPacket );
    }
}
