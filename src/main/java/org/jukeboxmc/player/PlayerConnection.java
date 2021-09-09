package org.jukeboxmc.player;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private final Server server;
    private final Channel rakNetSession;

    public PlayerConnection( Server server, Channel rakNetSession ) {
        this.server = server;
        this.rakNetSession = rakNetSession;
    }

    public Server getServer() {
        return this.server;
    }

    public Channel getRakNetSession() {
        return this.rakNetSession;
    }

    public void sendPacket( Packet packet, boolean direct ) {
        BinaryStream stream = new BinaryStream( PooledByteBufAllocator.DEFAULT.buffer() );
        packet.write(stream);
        if ( direct ) {
            this.rakNetSession.writeAndFlush( stream.getBuffer() );
        } else {
            this.rakNetSession.write( stream.getBuffer() );
        }
    }

    public void sendPacket( Packet packet ) {
        this.sendPacket( packet, false );
    }
}
