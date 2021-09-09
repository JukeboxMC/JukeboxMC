package jukeboxmc.network.raknet.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import network.ycc.raknet.pipeline.FlushTickHandler;

import java.util.zip.DataFormatException;

/**
 * @author Kaooot
 * @version 1.0
 */
public class PacketCompressor extends ChannelOutboundHandlerAdapter {

    public static final String NAME = "packet-compressor";

    private final int componentMaximum = 512;

    private ICompressor compressor;
    private CompositeByteBuf input;
    private CompositeByteBuf output;
    private boolean dirty;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        this.compressor = new ZipCompressor(true, 7, true);

        final ByteBufAllocator allocator = ctx.alloc();

        this.input = allocator.compositeDirectBuffer(this.componentMaximum);
        this.output = allocator.compositeDirectBuffer(this.componentMaximum);
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext context) throws Exception {
        ReferenceCountUtil.safeRelease(this.output);
        ReferenceCountUtil.safeRelease(this.input);

        this.compressor.close();

        super.handlerRemoved(context);
    }

    @Override
    public void write(final ChannelHandlerContext ctx, final Object message, final ChannelPromise promise) throws Exception {
        if (message instanceof ByteBuf) {
            final ByteBuf buffer = (ByteBuf) message;

            try {
                promise.trySuccess();

                if (!buffer.isReadable()) {
                    return;
                }

                int poolByteMaximum = 128 * 1024;
                if ( poolByteMaximum < this.input.readableBytes()) {
                    this.flush0(ctx);
                }

                final ByteBuf headerBuffer = ctx.alloc().directBuffer(8, 8);

                this.writeVarInt(buffer.readableBytes(), headerBuffer);

                this.input.addComponent(true, headerBuffer);
                this.input.addComponent(true, buffer.retain());

                this.dirty = true;

                if ( poolByteMaximum < this.output.readableBytes()) {
                    this.flush0(ctx);
                }

                FlushTickHandler.checkFlushTick(ctx.channel());
            } finally {
                ReferenceCountUtil.safeRelease(message);
            }
        } else {
            super.write(ctx, message, promise);
        }
    }

    @Override
    public void flush(final ChannelHandlerContext ctx) throws Exception {
        if (this.dirty) {
            this.flush0(ctx);
        }

        super.flush(ctx);
    }

    private void flush0(final ChannelHandlerContext ctx) throws DataFormatException {
        this.dirty = false;

        final ByteBufAllocator allocator = ctx.alloc();
        final ByteBuf output = allocator.directBuffer(this.input.readableBytes() / 4 + 16);

        this.compressor.process(this.input, output);
        this.input.release();
        this.input = allocator.compositeDirectBuffer(this.componentMaximum);

        ctx.write(output).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    private void writeVarInt(int value, final ByteBuf buffer) {
        while (true) {
            if (0 == (value & ~0x7F)) {
                buffer.writeByte(value);

                return;
            } else {
                buffer.writeByte((byte) ((value & 0x7F) | 0x80));
                value >>>= 7;
            }
        }
    }
}