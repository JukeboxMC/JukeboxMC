package org.jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.utils.BinaryStream;

import java.util.List;

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
public class ProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    public static final String NAME = "protocol-decoder";

    private final Server server;

    public ProtocolDecoder() {
        this.server = Server.getInstance();
    }

    @Override
    protected void decode( final ChannelHandlerContext ctx, final ByteBuf buffer, final List<Object> out ) {
        BinaryStream binaryStream = new BinaryStream( buffer );
        int packetId = binaryStream.readUnsignedVarInt();

        Packet packet = this.server.getPacketRegistry().getPacket( packetId );
        if ( packet != null ) {
            packet.read( binaryStream );
            out.add( packet );
        } else {
           this.server.getLogger().info( "Packet with the id " + packetId + " is missing!" );
        }
    }
}