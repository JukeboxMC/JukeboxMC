package org.jukeboxmc.network.raknet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.unix.UnixChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import network.ycc.raknet.RakNet;
import network.ycc.raknet.pipeline.UserDataCodec;
import network.ycc.raknet.server.channel.RakNetServerChannel;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.Protocol;
import org.jukeboxmc.network.raknet.compression.PacketCompressor;
import org.jukeboxmc.network.raknet.compression.PacketDecompressor;
import org.jukeboxmc.network.raknet.handler.UnconnectedPingHandler;
import org.jukeboxmc.network.raknet.protocol.ProtocolDecoder;
import org.jukeboxmc.network.raknet.protocol.ProtocolEncoder;
import org.jukeboxmc.network.raknet.protocol.ProtocolHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Kaooot, LucGamesYT
 * @version 1.0
 */
public class RakNetListener {

    private final long serverId;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    private final Server server;

    public RakNetListener( long serverId ) {
        this.serverId = serverId;
        this.server = Server.getInstance();
    }

    public void bind() {
        InetSocketAddress address = this.server.getAddress();

        try {
            this.bossGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            this.workerGroup = new DefaultEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group( this.bossGroup, this.workerGroup )
                    .channelFactory( () -> new RakNetServerChannel( () ->
                            Epoll.isAvailable() ? new EpollDatagramChannel() : new NioDatagramChannel() ) )
                    .option( RakNet.SERVER_ID, serverId )
                    .handler( new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel( final Channel channel ) {
                            channel.pipeline().addLast( new UnconnectedPingHandler() );
                        }
                    } )
                    .childHandler( new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel( final Channel channel ) {
                            ChannelConfig channelConfig = channel.config();
                            try {
                                channelConfig.setOption( ChannelOption.IP_TOS, 0x18 );
                            } catch ( final ChannelException ignored ) {

                            }
                            channelConfig.setAllocator( PooledByteBufAllocator.DEFAULT );

                            RakNet.Config rakNetConfig = (RakNet.Config) channelConfig;
                            rakNetConfig.setMaxQueuedBytes( 8 * 1024 * 1024 );

                            channel.pipeline().addFirst( "timeout", new ReadTimeoutHandler( 20, TimeUnit.SECONDS ) );
                            channel.pipeline().addLast( UserDataCodec.NAME, new UserDataCodec( Protocol.BATCH_PACKET ) );
                            channel.pipeline().addLast( PacketCompressor.NAME, new PacketCompressor() );
                            channel.pipeline().addLast( PacketDecompressor.NAME, new PacketDecompressor() );
                            channel.pipeline().addLast( ProtocolDecoder.NAME, new ProtocolDecoder() );
                            channel.pipeline().addLast( ProtocolEncoder.NAME, new ProtocolEncoder() );
                            channel.pipeline().addLast( ProtocolHandler.NAME, new ProtocolHandler() );
                        }

                        @Override
                        public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
                            cause.printStackTrace();
                        }
                    } );

            ChannelFutureListener channelFutureListener = channelFuture -> {
                this.server.getLogger().info( "JukeboxMC is now running on the port " + this.server.getAddress().getPort() );
            };


            if ( this.bossGroup.getClass().equals( EpollEventLoopGroup.class ) ) {
                serverBootstrap.option( UnixChannelOption.SO_REUSEPORT, true );
                serverBootstrap.option( EpollChannelOption.MAX_DATAGRAM_PAYLOAD_SIZE, 4 * 1024 );
            } else {
                serverBootstrap.option( ChannelOption.RCVBUF_ALLOCATOR,
                        new AdaptiveRecvByteBufAllocator( 4 * 1024, 64 * 1024, 256 * 1024 ) );
            }

            this.channelFuture = serverBootstrap.bind( address ).addListener( channelFutureListener );
        } catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.channelFuture.channel().closeFuture();
        } finally {
            this.workerGroup.shutdownGracefully();
            this.bossGroup.shutdownGracefully();
        }
    }
}