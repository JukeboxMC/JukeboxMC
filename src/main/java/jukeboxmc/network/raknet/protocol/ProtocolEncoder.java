package jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.utils.BinaryStream;

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
public class ProtocolEncoder extends MessageToByteEncoder<Packet> {

    public static final String NAME = "protocol-encoder";

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Packet packet, final ByteBuf out) {
        final BinaryStream stream = new BinaryStream(ctx.alloc().buffer());
        packet.write(stream);

        out.writeBytes(stream.getBuffer());
    }
}