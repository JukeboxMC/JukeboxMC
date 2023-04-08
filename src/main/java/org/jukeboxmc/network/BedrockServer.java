package org.jukeboxmc.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.BedrockPong;
import org.cloudburstmc.protocol.bedrock.BedrockServerSession;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v575.Bedrock_v575;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.PlayerConnection;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BedrockServer {

    public static final BedrockCodec CODEC = Bedrock_v575.CODEC;

    private final InetSocketAddress bindAddress;
    private final Server server;
    private Channel channel;

    public BedrockServer( InetSocketAddress bindAddress, Server server ) {
        this.bindAddress = bindAddress;
        this.server = server;
    }

    public void bind() {
        //TODO UPDATE MOTD
        BedrockPong bedrockPong = new BedrockPong();
        bedrockPong.edition( "MCPE" );
        bedrockPong.gameType( this.server.getGameMode().getIdentifier() );
        bedrockPong.motd( this.server.getMotd() );
        bedrockPong.subMotd( this.server.getSubMotd() );
        bedrockPong.playerCount( this.server.getOnlinePlayers().size() );
        bedrockPong.maximumPlayerCount( this.server.getMaxPlayers() );
        bedrockPong.ipv4Port( this.bindAddress.getPort() );
        bedrockPong.nintendoLimited( false );
        bedrockPong.protocolVersion( CODEC.getProtocolVersion() );
        bedrockPong.version( CODEC.getMinecraftVersion() );

        this.channel = new ServerBootstrap()
                .channelFactory( RakChannelFactory.server( NioDatagramChannel.class ) )
                .option( RakChannelOption.RAK_ADVERTISEMENT, bedrockPong.toByteBuf() )
                .group( new NioEventLoopGroup() )
                .childHandler( new BedrockServerInitializer() {
                    @Override
                    protected void initSession( BedrockServerSession session ) {
                        session.setCodec( CODEC );
                        BedrockServer.this.server.addPlayer( BedrockServer.this.server.addPlayerConnection( new PlayerConnection( BedrockServer.this.server, BedrockServer.this, session ) ).getPlayer() );
                    }
                } )
                .bind( this.bindAddress )
                .syncUninterruptibly().channel();
        this.server.getLogger().info( "Server started successfully at " + this.bindAddress.getHostString() + ":" + this.bindAddress.getPort() + "!" );
    }

    public void close() {
        this.channel.close().awaitUninterruptibly();
    }
}
