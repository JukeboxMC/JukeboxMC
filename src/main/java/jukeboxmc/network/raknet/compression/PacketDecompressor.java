package jukeboxmc.network.raknet.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PacketDecompressor extends MessageToMessageDecoder<ByteBuf> {

    public static final String NAME = "packet-decompressor";

    private ICompressor compressor;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        this.compressor = new ZipCompressor(false, 0, true);
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) throws Exception {
        this.compressor.close();

        super.handlerRemoved(ctx);
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf buffer, final List<Object> out) throws Exception {
        ByteBuf output = null;

        try {
            output = ctx.alloc().ioBuffer(buffer.readableBytes() << 2);

            this.compressor.process(buffer, output);

            while (output.isReadable()) {
                out.add(output.readRetainedSlice(this.readVarInt(output)));
            }
        } finally {
            ReferenceCountUtil.safeRelease(output);
        }
    }

    private int readVarInt(final ByteBuf buffer) {
        int value = 0;

        for (int shift = 0; 35 >= shift; shift += 7) {
            final byte head = buffer.readByte();

            value |= (head & 0x7F) << shift;

            if (0 == (head & 0x80)) {
                return value;
            }
        }

        throw new ArithmeticException("The VarInt is wider than 35-bit");
    }
}