package org.jukeboxmc.network.raknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.raknet.event.RakNetEventManager;
import org.jukeboxmc.network.raknet.protocol.*;
import org.jukeboxmc.network.raknet.utils.ServerInfo;
import org.jukeboxmc.utils.BinaryStream;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Listener {

    private Channel channel;
    private EventLoopGroup group;
    @Getter
    private InetSocketAddress address;
    @Getter
    private ServerInfo serverInfo;
    @Getter
    private RakNetEventManager rakNetEventManager;
    @Getter
    private long serverId;
    @Getter
    private boolean isRunning = false;

    private ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public Listener() {
        this.serverInfo = new ServerInfo( this );
        this.rakNetEventManager = new RakNetEventManager();
        this.serverId = UUID.randomUUID().getMostSignificantBits();
    }

    public boolean listen( InetSocketAddress socketAddress ) {
        this.address = socketAddress;
        try {
            Bootstrap socket = new Bootstrap();
            socket.group( this.group = (Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup()) );
            socket.option(ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT);
            socket.channel( Epoll.isAvailable() ? EpollDatagramChannel.class : NioDatagramChannel.class );
            socket.handler( new ChannelInboundHandlerAdapter() {
                @Override
                public void channelRead( ChannelHandlerContext ctx, Object msg ) {
                    DatagramPacket packet = (DatagramPacket) msg;
                    BinaryStream stream = new BinaryStream( packet.content() );
                    InetSocketAddress senderAddress = packet.sender();
                    Listener.this.handle( stream, senderAddress );
                    stream.release();
                }
            } );

            this.channel = socket.bind( this.address ).sync().channel();
            this.isRunning = true;
            this.tick();
        } catch ( Exception e ) {
            e.printStackTrace();
            this.isRunning = false;
        }
        return this.isRunning;
    }

    public void handle( BinaryStream datagramPacket, InetSocketAddress sender ) {
        ByteBuf buffer = datagramPacket.getBuffer();
        int packetId = buffer.getUnsignedByte( 0 );

        if ( packetId == Protocol.QUERY ) {
            return;
        }

        String token = sender.getHostName() + ":" + sender.getPort();
        if ( this.connections.containsKey( token ) ) {
            Connection connection = this.connections.get( token );
            connection.receive( buffer );
        } else {
            if ( packetId == Protocol.UNCONNECTED_PING ) {
                this.handleUnconnectedPing( buffer, sender );
            } else if ( packetId == Protocol.OPEN_CONNECTION_REQUEST_1 ) {
                this.handleOpenConnectionRequest1( buffer, sender );
            } else if ( packetId == Protocol.OPEN_CONNECTION_REQUEST_2 ) {
                this.handleOpenConnectionRequest2( buffer, sender );
            }
        }
    }

    private void handleUnconnectedPing( ByteBuf buffer, InetSocketAddress address ) {
        UnconnectedPing decodedPacket = new UnconnectedPing();
        decodedPacket.buffer = buffer;
        decodedPacket.read();

        UnconnectedPong packet = new UnconnectedPong();
        packet.setTime( System.currentTimeMillis() );
        packet.setServerGUID( this.serverId );
        packet.setServerID( this.serverInfo.toString() );

        this.sendPacket( packet, address );
    }

    private void handleOpenConnectionRequest1( ByteBuf buffer, InetSocketAddress sender ) {
        OpenConnectionRequest1 decodedPacket = new OpenConnectionRequest1();
        decodedPacket.buffer = buffer;
        decodedPacket.read();

        if ( decodedPacket.getProtocol() != 10 ) {
            IncompatibleProtocolVersion packet = new IncompatibleProtocolVersion();
            packet.setProtocol( (byte) 10 );
            packet.setServerGUID( this.serverId );
            this.sendPacket( packet, sender );
        }

        OpenConnectionReply1 packet = new OpenConnectionReply1();
        packet.setServerGUID( this.serverId );
        packet.setMtu( decodedPacket.getMtu() );
        this.sendPacket( packet, sender );
    }

    private void handleOpenConnectionRequest2( ByteBuf buffer, InetSocketAddress sender ) {
        OpenConnectionRequest2 decodedPacket = new OpenConnectionRequest2();
        decodedPacket.buffer = buffer;
        decodedPacket.read();

        OpenConnectionReply2 packet = new OpenConnectionReply2();
        packet.setServerGUID( this.serverId );
        packet.setMtu( decodedPacket.getMtu() );
        packet.setAddress( sender );
        this.sendPacket( packet, sender );

        String token = sender.getHostName() + ":" + sender.getPort();
        if ( !this.connections.containsKey( token ) ) {
            this.connections.put( token, new Connection( this, decodedPacket.getMtu(), sender ) );
        }
    }

    private void tick() {
        Timer timer = new Timer();
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                if ( isRunning ) {
                    for ( Connection connection : connections.values() ) {
                        connection.update( System.currentTimeMillis() );
                    }
                } else {
                    this.cancel();
                }
            }
        }, 0, 1 ); //Raknet tick
    }

    private void sendPacket( RakNetPacket rakNetPacket, InetSocketAddress address ) {
        if ( this.channel != null ) {
            rakNetPacket.write();
            this.channel.writeAndFlush( new DatagramPacket( rakNetPacket.buffer, address ) );
        }
    }

    public void sendBuffer( ByteBuf buffer, InetSocketAddress address ) {
        if ( this.channel != null ) {
            ByteBuf duplicate = buffer.duplicate();
            byte[] array = new byte[duplicate.readableBytes()];
            duplicate.readBytes( array );
            this.channel.writeAndFlush( new DatagramPacket( Unpooled.wrappedBuffer( array ), address ) );
        }
    }

    public void removeConnection( Connection connection, String reason ) {
        String token = connection.getSender().getHostName() + ":" + connection.getSender().getPort();
        if ( this.connections.containsKey( token ) ) {
            this.connections.get( token ).close();
            this.connections.remove( token );
        }
    }
}
