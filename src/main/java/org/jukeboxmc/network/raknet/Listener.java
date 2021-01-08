package org.jukeboxmc.network.raknet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.raknet.event.RakNetEventManager;
import org.jukeboxmc.network.raknet.event.intern.PlayerCloseConnectionEvent;
import org.jukeboxmc.network.raknet.protocol.*;
import org.jukeboxmc.network.raknet.utils.ServerInfo;
import org.jukeboxmc.utils.BinaryStream;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Listener {

    private Channel channel;
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


    private Map<InetSocketAddress, Connection> connections = new ConcurrentHashMap<>();
    private Queue<DatagramPacket> packets = new ConcurrentLinkedQueue<>();

    public Listener() {
        this.serverInfo = new ServerInfo( this );
        this.rakNetEventManager = new RakNetEventManager();
        this.serverId = UUID.randomUUID().getMostSignificantBits();
    }

    public boolean listen( InetSocketAddress socketAddress ) {
        this.address = socketAddress;
        try {
            Bootstrap socket = new Bootstrap();
            socket.group( ( Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup() ) );
            socket.option( ChannelOption.ALLOCATOR, ByteBufAllocator.DEFAULT );
            socket.channel( Epoll.isAvailable() ? EpollDatagramChannel.class : NioDatagramChannel.class );
            socket.handler( new ChannelInboundHandlerAdapter() {
                @Override
                public void channelRead( ChannelHandlerContext ctx, Object msg ) {
                    DatagramPacket datagramPacket = (DatagramPacket) msg;
                    InetSocketAddress sender = datagramPacket.sender();

                    if ( Listener.this.connections.containsKey( sender ) ) {
                        Connection connection = Listener.this.connections.get( sender );
                        connection.receive( datagramPacket.content() );
                    } else {
                        Listener.this.handle( new BinaryStream( datagramPacket.content() ), datagramPacket.sender() );
                    }
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

    public void handle( BinaryStream stream, InetSocketAddress sender ) {
        ByteBuf buffer = stream.getBuffer();
        int packetId = buffer.getUnsignedByte( 0 );

        if ( packetId == Protocol.UNCONNECTED_PING ) {
            this.handleUnconnectedPing( buffer, sender );
        } else if ( packetId == Protocol.OPEN_CONNECTION_REQUEST_1 ) {
            this.handleOpenConnectionRequest1( buffer, sender );
        } else if ( packetId == Protocol.OPEN_CONNECTION_REQUEST_2 ) {
            this.handleOpenConnectionRequest2( buffer, sender );
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
        if ( !this.connections.containsKey( sender ) ) {
            this.connections.put( sender, new Connection( this, decodedPacket.getMtu(), sender ) );
        }
    }

    private void tick() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate( () -> {
            try {
               /*
                DatagramPacket packet;
                while ( ( packet = this.packets.poll() ) != null ) {
                    this.handle( new BinaryStream( packet.content() ), packet.sender() );
                }
                */

                for ( Connection connection : connections.values() ) {
                    connection.update( System.currentTimeMillis() );
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.MILLISECONDS );
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
        InetSocketAddress sender = connection.getSender();
        if ( this.connections.containsKey( sender ) ) {
            this.connections.get( sender ).close();
            this.connections.remove( sender );
        }
        System.out.println( reason );
        this.getRakNetEventManager().callEvent( new PlayerCloseConnectionEvent( connection ) );
    }
}
