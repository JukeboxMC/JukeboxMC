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
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler;
import org.cloudburstmc.protocol.common.PacketSignal;
import org.jukeboxmc.Server;

import java.net.InetSocketAddress;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BedrockServer {

    public static final BedrockCodec CODEC = Bedrock_v575.CODEC;

    private final InetSocketAddress bindAddress;
    private final Server server;
    private Channel channel;
    private Consumer<BedrockServerSession> consumer;
    private Consumer<String> disconnectConsumer;
    private BiConsumer<BedrockPacket, BedrockServerSession> packetConsumer;

    public BedrockServer( InetSocketAddress bindAddress, Server server ) {
        this.bindAddress = bindAddress;
        this.server = server;
    }

    public void registerServerSession( Consumer<BedrockServerSession> consumer ) {
        this.consumer = consumer;
    }

    public void registerPacketHandler( BiConsumer<BedrockPacket, BedrockServerSession> packetConsumer ) {
        this.packetConsumer = packetConsumer;
    }

    public void registerDisconnectHandler( Consumer<String> disconnectConsumer ) {
        this.disconnectConsumer = disconnectConsumer;
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
                        BedrockServer.this.consumer.accept( session );
                        session.setPacketHandler( new BedrockPacketHandler() {
                            @Override
                            public PacketSignal handlePacket( BedrockPacket packet ) {
                                BedrockServer.this.packetConsumer.accept( packet, session );
                                return PacketSignal.HANDLED;
                            }

                            @Override
                            public void onDisconnect( String reason ) {
                                BedrockServer.this.disconnectConsumer.accept( reason );
                                BedrockPacketHandler.super.onDisconnect( reason );
                            }
                        } );
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
