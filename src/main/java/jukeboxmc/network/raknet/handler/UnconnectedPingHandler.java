package jukeboxmc.network.raknet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.ReferenceCountUtil;
import network.ycc.raknet.RakNet;
import network.ycc.raknet.packet.UnconnectedPing;
import network.ycc.raknet.packet.UnconnectedPong;
import network.ycc.raknet.server.pipeline.UdpPacketHandler;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.raknet.type.ServerInfo;

import java.net.InetSocketAddress;

/**
 * @author Kaooot
 * @version 1.0
 */
public class UnconnectedPingHandler extends UdpPacketHandler<UnconnectedPing> {

    private final Server server;

    public UnconnectedPingHandler() {
        super( UnconnectedPing.class );
        this.server = Server.getInstance();
    }

    @Override
    protected void handle( ChannelHandlerContext ctx, InetSocketAddress socketAddress, UnconnectedPing unconnectedPing ) {
        Channel channel = ctx.channel();
        RakNet.Config rakNetConfig = (RakNet.Config) channel.config();

        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerId( this.server.getServerId() );
        serverInfo.setMotd( this.server.getMotd() );
        serverInfo.setSubmotd( this.server.getSubmotd() );
        serverInfo.setMaxPlayers( this.server.getMaxPlayers() );
        serverInfo.setDefaultGameMode( this.server.getDefaultGameMode() );

        UnconnectedPong unconnectedPong = new UnconnectedPong();
        unconnectedPong.setClientTime( unconnectedPing.getClientTime() );
        unconnectedPong.setServerId( rakNetConfig.getServerId() );
        unconnectedPong.setMagic( rakNetConfig.getMagic() );
        unconnectedPong.setInfo( serverInfo.toString() );

        ByteBuf buffer = ctx.alloc().directBuffer( unconnectedPong.sizeHint() );

        try {
            rakNetConfig.getCodec().encode( unconnectedPong, buffer );

            for ( int i = 0; i < 3; i++ ) {
                channel.writeAndFlush( new DatagramPacket( buffer.retainedSlice(), socketAddress ) )
                        .addListener( ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE );
            }
        } finally {
            ReferenceCountUtil.safeRelease( unconnectedPong );
            buffer.release();
        }
    }
}