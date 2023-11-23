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
import org.cloudburstmc.protocol.bedrock.codec.v618.Bedrock_v618;
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.PlayerConnection;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BedrockServer {

    public static final BedrockCodec CODEC = Bedrock_v622.CODEC.toBuilder().build();

    private final InetSocketAddress bindAddress;
    private final Server server;
    private final BedrockPong bedrockPong;
    private Channel channel;

    public BedrockServer( InetSocketAddress bindAddress, Server server ) {
        this.bindAddress = bindAddress;
        this.server = server;
        this.bedrockPong = new BedrockPong();
        this.bedrockPong.edition( "MCPE" );
        this.bedrockPong.gameType( this.server.getGameMode().getIdentifier() );
        this.bedrockPong.motd( this.server.getMotd() );
        this.bedrockPong.subMotd( this.server.getSubMotd() );
        this.bedrockPong.playerCount( this.server.getOnlinePlayers().size() );
        this.bedrockPong.maximumPlayerCount( this.server.getMaxPlayers() );
        this.bedrockPong.ipv4Port( this.bindAddress.getPort() );
        this.bedrockPong.nintendoLimited( false );
        this.bedrockPong.protocolVersion( CODEC.getProtocolVersion() );
        this.bedrockPong.version( CODEC.getMinecraftVersion() );
    }

    public void bind() {
        this.channel = new ServerBootstrap()
                .channelFactory( RakChannelFactory.server( NioDatagramChannel.class ) )
                .option( RakChannelOption.RAK_ADVERTISEMENT, this.bedrockPong.toByteBuf() )
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

    public void updateMotd() {
        this.bedrockPong.edition( "MCPE" );
        this.bedrockPong.gameType( this.server.getGameMode().getIdentifier() );
        this.bedrockPong.motd( this.server.getMotd() );
        this.bedrockPong.subMotd( this.server.getSubMotd() );
        this.bedrockPong.playerCount( this.server.getOnlinePlayers().size() );
        this.bedrockPong.maximumPlayerCount( this.server.getMaxPlayers() );
        this.bedrockPong.ipv4Port( this.bindAddress.getPort() );
        this.bedrockPong.nintendoLimited( false );
        this.bedrockPong.protocolVersion( CODEC.getProtocolVersion() );
        this.bedrockPong.version( CODEC.getMinecraftVersion() );
        this.channel.config().setOption( RakChannelOption.RAK_ADVERTISEMENT, this.bedrockPong.toByteBuf() );
    }

    public void close() {
        this.channel.close().awaitUninterruptibly();
    }
}
