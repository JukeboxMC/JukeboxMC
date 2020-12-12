package org.jukeboxmc.network.handler;

import org.jukeboxmc.network.packet.LoginPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayStatusPacket;
import org.jukeboxmc.network.packet.ResourcePacksInfoPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.PlayerConnection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoginHandler implements PacketHandler{

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerConnection playerConnection = player.getPlayerConnection();
        LoginPacket loginPacket = (LoginPacket) packet;

        //TODO Handle ChainData

        PlayStatusPacket playStatusPacket = new PlayStatusPacket();
        playStatusPacket.setStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );
        playerConnection.sendPacket( playStatusPacket );

        ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
        resourcePacksInfoPacket.setScripting( false );
        resourcePacksInfoPacket.setForceAccept( false );
        playerConnection.sendPacket( resourcePacksInfoPacket );
    }
}
