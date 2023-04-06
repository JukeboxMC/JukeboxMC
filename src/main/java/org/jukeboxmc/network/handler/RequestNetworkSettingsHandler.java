package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.BedrockServer;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class RequestNetworkSettingsHandler implements PacketHandler<RequestNetworkSettingsPacket> {

    @Override
    public void handle( RequestNetworkSettingsPacket packet, Server server, Player player ) {
        if ( player.getPlayerConnection().isLoggedIn() ) {
            player.getPlayerConnection().disconnect( "Player is already logged in." );
            return;
        }
        int protocolVersion = packet.getProtocolVersion();
        int currentProtocol = BedrockServer.CODEC.getProtocolVersion();

        if ( protocolVersion != currentProtocol ) {
            player.getPlayerConnection().sendPlayStatus( protocolVersion > currentProtocol ? PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD : PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD );
            return;
        }

        PacketCompressionAlgorithm compressionAlgorithm = server.getCompressionAlgorithm();

        NetworkSettingsPacket networkSettingsPacket = new NetworkSettingsPacket();
        networkSettingsPacket.setCompressionThreshold( 0 );
        networkSettingsPacket.setCompressionAlgorithm( compressionAlgorithm );

        player.getPlayerConnection().sendPacketImmediately( networkSettingsPacket );
        player.getPlayerConnection().getSession().setCompression( compressionAlgorithm );
    }
}