package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.network.packet.LoginPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.PlayerConnection;

import java.util.Locale;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerConnection playerConnection = player.getPlayerConnection();
        LoginPacket loginPacket = (LoginPacket) packet;

        player.setName( loginPacket.getUsername() );
        player.setXuid( loginPacket.getXuid() );
        player.setUUID( loginPacket.getUuid() );
        player.setSkin( loginPacket.getSkin() );
        player.setDeviceInfo( loginPacket.getDeviceInfo() );
        player.setLocale( loginPacket.getLanguageCode() != null ? Locale.forLanguageTag( loginPacket.getLanguageCode().replace( "_", "-" ) ) : Locale.US );
        player.incrementEntityId();
        player.setNameTag( player.getName() );
        player.setNameTagVisible( true );
        player.setNameTagAlwaysVisible( true );

        if ( ( player.getXuid() == null || player.getXuid().isEmpty() ) && Server.getInstance().getServerConfig().getBoolean( "online-mode" ) ) {
            player.disconnect( "Server is online-mode" );
            return;
        } else {
            playerConnection.sendStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );
        }

        playerConnection.sendResourcePackInfo();
    }
}