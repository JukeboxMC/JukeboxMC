package org.jukeboxmc.network.raknet.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.handler.PacketHandler;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.PlayerConnection;

import java.net.InetSocketAddress;

/**
 * Copyright (c) 2021, TerracottaMC
 * All rights reserved.
 *
 * <p>
 * This project is licensed under the BSD 3-Clause License which
 * can be found in the root directory of this source tree
 *
 * @author Kaooot
 * @version 1.0
 */
public class ProtocolHandler extends SimpleChannelInboundHandler<Packet> {

    public static final String NAME = "protocol-handler";

    private final Server server;

    public ProtocolHandler() {
        this.server = Server.getInstance();
    }

    @Override
    protected void channelRead0( ChannelHandlerContext ctx, Packet packet ) {
        InetSocketAddress clientAddress = (InetSocketAddress) ctx.channel().remoteAddress();

        this.server.addToMainThread( () -> {
            PacketHandler<Packet> packetHandler = (PacketHandler<Packet>) this.server.getPacketRegistry().getPacketHandler( packet.getClass() );
            if ( packetHandler != null ) {
                packetHandler.handle( packet, this.server, this.server.getPlayer( clientAddress ) );
            }
        } );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        if ( cause instanceof ReadTimeoutException ) {
            InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            this.server.addToMainThread( () -> {
                Player player = this.server.getPlayer( socketAddress );
                if ( player != null ) {
                    player.leaveServer( "Timeout" );
                } else {
                    this.server.getLogger().info( socketAddress.getHostName() + ":" + socketAddress.getPort() + " logged out reason: Timeout" );
                }
            } );
            ctx.channel().close();
        } else {
            this.server.getLogger().info( cause );
            super.exceptionCaught( ctx, cause );
        }
    }

    @Override
    public void channelActive( ChannelHandlerContext ctx ) {
        Channel channel = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        this.server.addToMainThread( () -> {
            if ( Server.getInstance().getPlayer( socketAddress ) == null ) {
                Server.getInstance().addPlayer( new Player( new PlayerConnection( this.server, channel ) ) );
            }
        } );
    }

    @Override
    public void channelInactive( ChannelHandlerContext ctx ) throws Exception {
        Channel channel = ctx.channel();
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        this.server.addToMainThread( () -> {
            Player player = this.server.getPlayer( socketAddress );
            if ( player != null ) {
                player.leaveServer( "Disconnect" );
            }
        } );
        channel.close();
    }
}