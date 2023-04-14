package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerLoginEvent;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.data.LoginData;
import org.jukeboxmc.resourcepack.ResourcePack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler<LoginPacket> {

    @Override
    public void handle( LoginPacket packet, Server server, Player player ) {
        player.getPlayerConnection().setLoginData( new LoginData( packet ) );

        PlayerLoginEvent playerLoginEvent = new PlayerLoginEvent( player );

        if ( !player.getPlayerConnection().getLoginData().isXboxAuthenticated() ) {
            playerLoginEvent.setResult( PlayerLoginEvent.Result.XBOX_AUTHENTICATED );
            playerLoginEvent.setKickReason( "You must be logged in with your xbox account." );
        }

        if ( Server.getInstance().getOnlinePlayers().size() >= Server.getInstance().getMaxPlayers() ) {
            playerLoginEvent.setResult( PlayerLoginEvent.Result.SERVER_FULL );
            playerLoginEvent.setKickReason( "Server is full." );
        }
        if ( server.hasWhitelist() ) {
            if ( !server.isPlayerOnWhitelist( player.getName() ) ) {
                playerLoginEvent.setResult( PlayerLoginEvent.Result.WHITELIST );
                playerLoginEvent.setKickReason( "You are not on the whitelist" );
            }
        }
        Server.getInstance().getPluginManager().callEvent( playerLoginEvent );

        if ( playerLoginEvent.getResult() != PlayerLoginEvent.Result.ALLOWED ) {
            player.kick( playerLoginEvent.getKickReason() );
            return;
        }

        player.getPlayerConnection().sendPlayStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );

        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        for ( ResourcePack resourcePack : server.getResourcePackManager().retrieveResourcePacks() ) {
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
