package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.RequestNetworkSettingsPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.Network;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class RequestNetworkSettingsHandler implements PacketHandler<RequestNetworkSettingsPacket> {

    @Override
    public void handle( RequestNetworkSettingsPacket packet, Server server, Player player ) {
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

        PacketCompressionAlgorithm compressionAlgorithm = Server.getInstance().getCompressionAlgorithm();

        player.getPlayerConnection().getSession().setPacketCodec( Network.CODEC );

        NetworkSettingsPacket networkSettingsPacket = new NetworkSettingsPacket();
        networkSettingsPacket.setCompressionThreshold( 0 );
        networkSettingsPacket.setCompressionAlgorithm( compressionAlgorithm );

        player.getPlayerConnection().sendPacketImmediately( networkSettingsPacket );
        player.getPlayerConnection().getSession().setCompression( compressionAlgorithm );
    }
}